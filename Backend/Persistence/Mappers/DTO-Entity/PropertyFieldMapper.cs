using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class PropertyFieldMapper
    {
        public static PropertyFieldDto ToDto(PropertyFieldEntity entity)
        {
            return new PropertyFieldDto
            {
                Id = entity.ID,
                GameFieldID = entity.GameFieldID,
                OwnerId = entity.OwnerID,
                Houses = entity.Houses,
                Hotels = entity.Hotels
            };
        }
        public static PropertyFieldEntity ToEntity(PropertyFieldDto dto,int boardId)
        {
            return new PropertyFieldEntity
            {
                ID = dto.Id,
                GameFieldID = dto.GameFieldID,
                OwnerID = dto.OwnerId,
                Houses = dto.Houses,
                Hotels = dto.Hotels,
                BoardId = boardId
            };
        }
    }
}
