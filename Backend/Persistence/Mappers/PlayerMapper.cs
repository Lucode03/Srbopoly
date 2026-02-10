using Backend.Domain;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class PlayerMapper
    {
        public static PlayerEntity ToEntity(Player player)
        {
            return new PlayerEntity
            {
                Balance = player.Balance,
                Position = player.Position,
                Color = player.Color,
                IsInJail = player.IsInJail,
                User = UserMapper.ToEntity(player)
            };
        }

        public static Player ToBusiness(PlayerEntity entity)
        {
            return new Player
            {
                ID = entity.User.ID,
                Username = entity.User.Username,
                Points = entity.User.Points,
            
                Balance = entity.Balance,
                Position = entity.Position,
                Color = entity.Color,
                IsInJail = entity.IsInJail,
                Properties = new List<PropertyField>()
            };
        }
    }
}
