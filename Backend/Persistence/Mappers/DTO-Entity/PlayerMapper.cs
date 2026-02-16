using Backend.Domain;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class PlayerMapperDE
    {
        public static PlayerDto ToDto(PlayerEntity entity)
        {
            return new PlayerDto
            {
                Id = entity.ID,
                Username = entity.User?.Username ?? string.Empty,
                Balance = entity.Balance,
                Position = entity.Position,
                Color = entity.Color,
                IsInJail = entity.IsInJail,
                UserId = entity.UserId
            };
        }

        public static PlayerEntity ToEntity(PlayerDto dto,int gameId)
        {
            return new PlayerEntity
            {
                ID = dto.Id,
                Balance = dto.Balance,
                Position = dto.Position,
                Color = dto.Color,
                IsInJail = dto.IsInJail,
                UserId = dto.UserId,
                GameId = gameId
            };
        }
    }
}
