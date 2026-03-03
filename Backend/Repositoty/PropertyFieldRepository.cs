namespace Backend.Repositories;

using Backend.Domain;
using Backend.Persistence.DTO;
using Backend.Persistence.Entities;
using Backend.Persistence.Mappers;

public class PropertyFieldRepository
{
    private readonly SrbopolyContext _context;

    public PropertyFieldRepository(SrbopolyContext context)
    {
        _context = context;
    }

    public async Task<PropertyField> CreateAsync(int boardId, PropertyField field)
    {
        var board = await _context.Boards.FindAsync(boardId);
        if (board == null)
            throw new KeyNotFoundException($"Board sa ID {boardId} nije pronađen.");

        var propertDto = PropertyFieldMapper.ToDTO(field);
        var entity = PropertyFieldMapperDE.ToEntity(propertDto, boardId);

        await _context.PropertyFields.AddAsync(entity);
        await _context.SaveChangesAsync();

        return field;
    }

    public async Task<PropertyField?> GetByIdAsync(int id)
    {
        var entity = await _context.PropertyFields.FindAsync(id);
        if (entity == null) return null;

        var propertyDto = PropertyFieldMapperDE.ToDto(entity);
        return PropertyFieldMapper.ToBusiness(propertyDto, new List<Player>());
    }

    public async Task<List<PropertyField>> GetAllForBoardAsync(int boardId)
    {
        var entities = await _context.PropertyFields
            .Where(f => f.BoardId == boardId)
            .ToListAsync();

        var dtos = entities.Select(PropertyFieldMapperDE.ToDto).ToList();
         return dtos.Select(dto => PropertyFieldMapper.ToBusiness(dto, new List<Player>())).ToList();
    }

    public async Task<PropertyField> UpdateAsync(int id, PropertyField field)
    {
        var entity = await _context.PropertyFields.FindAsync(id);
        if (entity == null)
            throw new KeyNotFoundException($"PropertyField sa ID {id} nije pronađen.");

        entity.GameFieldID = field.GameFieldID;
        entity.OwnerID = field.Owner.ID;
        entity.Houses = field.Houses;
        entity.Hotels = field.Hotels;

        await _context.SaveChangesAsync();

        var propertyDto = PropertyFieldMapperDE.ToDto(entity);
        return PropertyFieldMapper.ToBusiness(propertyDto, new List<Player>());
    }

    public async Task DeleteAsync(int id)
    {
        var entity = await _context.PropertyFields.FindAsync(id);
        if (entity == null)
            throw new KeyNotFoundException($"PropertyField sa ID {id} nije pronađen.");

        _context.PropertyFields.Remove(entity);
        await _context.SaveChangesAsync();
    }

    public async Task<PropertyField> UpdateOwnerAsync(int boardId, int gameFieldId, int playerId)
    {
        var entity = await _context.PropertyFields
            .FirstOrDefaultAsync(f => f.BoardId == boardId && f.GameFieldID == gameFieldId);

        if (entity == null)
            throw new KeyNotFoundException($"PropertyField na poziciji {gameFieldId} za Board ID {boardId} nije pronađen.");

        entity.OwnerID = playerId;

        await _context.SaveChangesAsync();

        var propertyDto = PropertyFieldMapperDE.ToDto(entity);
        return PropertyFieldMapper.ToBusiness(propertyDto, new List<Player>());
    }
}