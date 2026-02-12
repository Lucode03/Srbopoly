namespace Backend.Repository;

using Backend.Domain;
using Backend.Persistence.Entities;
using Backend.Persistence.Mappers;
using Backend.Persistence.DTO;

public class GameRepository : IGameRepository
{
    private readonly SrbopolyContext _context;

    public GameRepository(SrbopolyContext context)
    {
        _context = context;
    }

    public async Task<Game> CreateAsync(Game game)
    {
        var gameDto =GameMapper.ToEntity(game);
        var entity = GameMapperDE.ToEntity(gameDto);
        await _context.Games.AddAsync(entity);
        await _context.SaveChangesAsync();
        return game;
    }

    public async Task<Game?> GetByIdAsync(int id)
    {
        var entity = await _context.Games
            .Include(g => g.Players)
                .ThenInclude(p => p.User)
            .Include(g => g.Board)
                .ThenInclude(b => b.PropertyFields)
            .FirstOrDefaultAsync(g => g.ID == id);

        if (entity == null)
            return null;

        var dto = GameMapperDE.ToDto(entity);

        return GameMapper.ToBusiness(dto);
    }

    public async Task<List<Game>> GetAllAsync()
    {
        var entities = await _context.Games
            .Include(g => g.Players)
                .ThenInclude(p => p.User)
            .Include(g => g.Board)
                .ThenInclude(b => b.PropertyFields)
            .ToListAsync();

        var dtos = entities.Select(GameMapperDE.ToDto).ToList();
        var businessGames = dtos.Select(GameMapper.ToBusiness).ToList();

        return businessGames;
    }

    public async Task SaveAsync(Game game)
    {
        var dto = GameMapper.ToEntity(game);

        var entity = await _context.Games
            .Include(g => g.Players)
            .Include(g => g.Board)
                .ThenInclude(b => b.PropertyFields)
            .FirstOrDefaultAsync(g => g.ID == dto.Id);

        if (entity == null)
        {
            entity = GameMapperDE.ToEntity(dto);
            await _context.Games.AddAsync(entity);
        }
        else
        {
            UpdateEntity(entity, dto);
        }

        await _context.SaveChangesAsync();
    }

    public async Task DeleteAsync(Game game)
    {
        var entity = await _context.Games.FindAsync(game.ID);
        if (entity != null)
        {
            _context.Games.Remove(entity);
            await _context.SaveChangesAsync();
        }
    }

    private void UpdateEntity(GameEntity entity, GameDto dto)
    {
        entity.Status = dto.Status;
        entity.MaxTurns = dto.MaxTurns;
        entity.CurrentTurn = dto.CurrentTurn;
        entity.CurrentPlayerIndex = dto.CurrentPlayerIndex;
        entity.RewardCardsDeckIds = dto.RewardCardsDeckIds;
        entity.SurpriseCardsDeckIds = dto.SurpriseCardsDeckIds;
    }

    public async Task SaveChangesAsync()
    {
        await _context.SaveChangesAsync();
    }
}