namespace Backend.Repository;

using Backend.Domain;

public interface IGameRepository
{
    Task<Game> CreateAsync(Game game);
    Task<Game?> GetByIdAsync(int id);
    Task<List<Game>> GetAllAsync();
    Task DeleteAsync(Game game);
    Task SaveChangesAsync();
}