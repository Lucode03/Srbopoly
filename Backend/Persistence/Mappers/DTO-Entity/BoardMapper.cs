using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class BoardMapperDE
    {
        public static BoardDto ToDto(BoardEntity entity)
        {
            return new BoardDto
            {
                Id = entity.ID,
                PropertyFields = entity.PropertyFields?
                    .Select(PropertyFieldMapperDE.ToDto)
                    .ToList() ?? new List<PropertyFieldDto>()
            };
        }
        public static BoardEntity ToEntity(BoardDto dto, int gameId)
        {
            return new BoardEntity
            {
                ID = dto.Id,
                GameId = gameId,
                PropertyFields = dto.PropertyFields?
                    .Select(PropertyFieldMapperDE.ToEntity)
                    .ToList()
            };
        }
    }
}
