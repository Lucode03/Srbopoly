using Backend.Domain;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class PlayerMapper
    {
        public static PlayerDto ToDTO(Player player)
        {
            return new PlayerDto
            {
                Balance = player.Balance,
                Position = player.Position,
                Color = player.Color,
                IsInJail = player.IsInJail            
            };
        }

        public static Player ToBusiness(PlayerDto dto)
        {
            return new Player
            {
                Balance = dto.Balance,
                Position = dto.Position,
                Color = dto.Color,
                IsInJail = dto.IsInJail,
                Properties = new List<PropertyField>()
            };
        }
    }
}
