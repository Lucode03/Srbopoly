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
                
            };
        }

        public static Player ToBusiness(PlayerEntity entity)
        {
            return new Player
            {
                
            };
        }
    }
}
