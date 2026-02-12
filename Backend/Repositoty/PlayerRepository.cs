namespace Backend.Repositories;

using Backend.Domain;
using Backend.Persistence.DTO;
using Backend.Persistence.Entities;
using Backend.Persistence.Mappers;
using Backend.Persistence.Records;

public class PlayerRepository
{
    private readonly SrbopolyContext _context;

    public PlayerRepository(SrbopolyContext context)
    {
        _context = context;
    }

    public async Task<Player> CreatePlayerAsync(CreatePlayerRequest request)
    {
        var userEntity = await _context.Users.FindAsync(request.UserId);
        if (userEntity == null)
            throw new KeyNotFoundException($"User sa ID {request.UserId} ne postoji.");

        var gameEntity = await _context.Games.FindAsync(request.GameId);
        if (gameEntity == null)
            throw new KeyNotFoundException($"Game sa ID {request.GameId} ne postoji.");

        if (request.Balance < 0)
            throw new InvalidOperationException("Balance ne može biti negativan.");
        if (request.Position < 0)
            throw new InvalidOperationException("Position ne može biti negativan.");

        var playerEntity = new PlayerEntity
        {
            UserId = request.UserId,
            Balance = request.Balance,
            Position = request.Position,
            Color = request.Color,
            IsInJail = request.IsInJail
        };

        await _context.Players.AddAsync(playerEntity);
        await _context.SaveChangesAsync();

        var playerDto = PlayerMapperDE.ToDto(playerEntity);
        return PlayerMapper.ToBusiness(playerDto);
    }

    public async Task<Player?> GetByIdAsync(int id)
    {
        var entity = await _context.Players
            .Include(p => p.User)
            .FirstOrDefaultAsync(p => p.ID == id);

        if (entity == null) return null;

        var playerDto = PlayerMapperDE.ToDto(entity);
        return PlayerMapper.ToBusiness(playerDto);
    }

    public async Task<List<Player>> GetAllAsync()
    {
        var entities = await _context.Players
            .Include(p => p.User)
            .ToListAsync();

        var dtos = entities.Select(PlayerMapperDE.ToDto).ToList();
        return dtos.Select(PlayerMapper.ToBusiness).ToList();
    }

    public async Task DeleteAsync(int id)
    {
        var entity = await _context.Players.FindAsync(id);
        if (entity == null) throw new KeyNotFoundException($"Player sa ID {id} nije pronađen.");

        _context.Players.Remove(entity);
        await _context.SaveChangesAsync();
    }
}