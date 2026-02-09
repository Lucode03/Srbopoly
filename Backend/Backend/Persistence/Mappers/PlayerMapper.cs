using Backend.Domain;
using Backend.Persistence.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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
