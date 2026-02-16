namespace Backend.Repositories;

using Backend.Domain;
using Backend.Persistence.DTO;
using Backend.Persistence.Entities;
using Backend.Persistence.Mappers;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

public class UserRepository
{
    private readonly SrbopolyContext _context;

    public UserRepository(SrbopolyContext context)
    {
        _context = context;
    }

    public async Task<User> CreateUserAsync(string username)
    {
        var exists = await _context.Users.AnyAsync(u => u.Username == username);
        if (exists) throw new InvalidOperationException("Username već postoji.");

        var userEntity = new UserEntity
        {
            Username = username,
            Points = 0
        };

        await _context.Users.AddAsync(userEntity);
        await _context.SaveChangesAsync();

        var userDto = UserMapperDE.ToDto(userEntity);
        return UserMapper.ToBusiness(userDto);
    }

    public async Task<User?> GetByIdAsync(int id)
    {
        var entity = await _context.Users.FindAsync(id);
        if(entity == null)
            return null;
        var userDto = UserMapperDE.ToDto(entity);
        return UserMapper.ToBusiness(userDto);
    }

    public async Task<User?> GetByUsernameAsync(string username)
    {
        var entity = await _context.Users.FirstOrDefaultAsync(u => u.Username == username);
        if(entity == null)
            return null;
        var userDto = UserMapperDE.ToDto(entity);
        return UserMapper.ToBusiness(userDto);
    }

    public async Task<User> AddPointsAsync(int id, int points)
    {
        var user = await _context.Users.FindAsync(id);
        if (user == null) throw new KeyNotFoundException($"Korisnik sa ID {id} nije pronađen.");

        if (user.Points + points < 0)
            throw new InvalidOperationException("Korisnik ne može imati negativan broj bodova.");

        user.Points += points;
        await _context.SaveChangesAsync();


        var userDto = UserMapperDE.ToDto(user);
        return UserMapper.ToBusiness(userDto);
    }

    public async Task DeleteByIdAsync(int id)
    {
        var user = await _context.Users.FindAsync(id);
        if (user == null) throw new KeyNotFoundException($"Korisnik sa ID {id} nije pronađen.");

        _context.Users.Remove(user);
        await _context.SaveChangesAsync();
    }

    public async Task DeleteByUsernameAsync(string username)
    {
        var user = await _context.Users.FirstOrDefaultAsync(u => u.Username == username);
        if (user == null) throw new KeyNotFoundException($"Korisnik sa username '{username}' nije pronađen.");

        _context.Users.Remove(user);
        await _context.SaveChangesAsync();
    }

    public async Task<List<User>> GetAllAsync()
    {
        var users = await _context.Users.ToListAsync();

        var dtos = users.Select(UserMapperDE.ToDto).ToList();
        return dtos.Select(UserMapper.ToBusiness).ToList();
    }

    public async Task<List<User>> GetTopUsersAsync(int top)
    {
        IQueryable<UserEntity> query = _context.Users.OrderByDescending(u => u.Points);
        if (top > 0) query = query.Take(top);

        var users = await query.ToListAsync();
        var dtos = users.Select(UserMapperDE.ToDto).ToList();
        return dtos.Select(UserMapper.ToBusiness).ToList();
    }
}