namespace Backend.Repositories;

using Backend.Domain;
using Backend.Persistence.DTO;
using Backend.Persistence.Entities;
using Backend.Persistence.Mappers;

public class CardRepository
{
    private readonly SrbopolyContext _context;

    public CardRepository(SrbopolyContext context)
    {
        _context = context;
    }

    public async Task<Card> CreateCardAsync(int gameId, Card card)
    {
        var gameEntity = await _context.Games.FindAsync(gameId);
        if (gameEntity == null)
            throw new KeyNotFoundException($"Game sa ID {gameId} nije pronađen.");

        if (card.GameCardID < 0)
            throw new InvalidOperationException("GameCardID mora biti pozitivan.");

        var cardDto = CardMapper.ToDto(card);
        var cardEntity = CardMapperDE.ToEntity(cardDto);

        await _context.Cards.AddAsync(cardEntity);
        await _context.SaveChangesAsync();

        return card;
    }

    public async Task<Card?> GetByIdAsync(int id)
    {
        var entity = await _context.Cards
            .Include(c => c.Game)
            .FirstOrDefaultAsync(c => c.ID == id);

        if (entity == null) return null;

        var cardDto = CardMapperDE.ToDto(entity);
        return CardMapper.ToBusiness(cardDto);
    }

    public async Task<List<Card>> GetByGameAsync(int gameId)
    {
        var game = await _context.Games.FindAsync(gameId);
        if (game == null)
            throw new KeyNotFoundException($"Game sa ID {gameId} nije pronađen.");

        var entities = await _context.Cards
            .Where(c => c.GameId == gameId)
            .ToListAsync();

        var dtos = entities.Select(CardMapperDE.ToDto).ToList();
        return dtos.Select(CardMapper.ToBusiness).ToList();
    }

    public async Task DeleteAsync(int id)
    {
        var entity = await _context.Cards.FindAsync(id);
        if (entity == null) throw new KeyNotFoundException($"Card sa ID {id} nije pronađena.");

        _context.Cards.Remove(entity);
        await _context.SaveChangesAsync();
    }
}