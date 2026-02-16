namespace Backend.Repositories;

using Backend.Domain;
using Backend.Persistence.DTO;
using Backend.Persistence.Entities;
using Backend.Persistence.Mappers;
using Microsoft.EntityFrameworkCore;

public class BoardRepository
{
    private readonly SrbopolyContext _context;

    public BoardRepository(SrbopolyContext context)
    {
        _context = context;
    }

    public async Task<Board> CreateBoardAsync(int gameId)
    {
        var boardEntity = new BoardEntity
        {
            GameId = gameId,
            PropertyFields = new List<PropertyFieldEntity>()
        };

        await _context.Boards.AddAsync(boardEntity);
        await _context.SaveChangesAsync();

        var boardDto = BoardMapperDE.ToDto(boardEntity);
        return BoardMapper.ToBusiness(boardDto, new List<Player>());
    }

    public async Task<Board?> GetBoardByGameAsync(int gameId)
    {
        var boardEntity = await _context.Boards
            .Include(b => b.PropertyFields)
            .FirstOrDefaultAsync(b => b.GameId == gameId);

        if (boardEntity == null) return null;

        var boardDto = BoardMapperDE.ToDto(boardEntity);
        return BoardMapper.ToBusiness(boardDto, new List<Player>());
    }

    public async Task<PropertyField> AddPropertyFieldAsync(int boardId, PropertyField field)
    {
        var boardEntity = await _context.Boards
            .Include(b => b.PropertyFields)
            .FirstOrDefaultAsync(b => b.ID == boardId);

        if (boardEntity == null) throw new KeyNotFoundException($"Board sa ID {boardId} nije pronađen.");

        var fieldDto = PropertyFieldMapper.ToDTO(field);
        var fieldEntity = PropertyFieldMapperDE.ToEntity(fieldDto, boardId);
        boardEntity.PropertyFields ??= new List<PropertyFieldEntity>();
        boardEntity.PropertyFields.Add(fieldEntity);

        await _context.SaveChangesAsync();

        var addedDto = PropertyFieldMapperDE.ToDto(fieldEntity);
        return PropertyFieldMapper.ToBusiness(addedDto, new List<Player>());
    }
}