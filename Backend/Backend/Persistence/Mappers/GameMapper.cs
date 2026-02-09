using Backend.Domain;
using Backend.Persistence.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Persistence.Mappers
{
    public static class GameMapper
    {
        public static GameEntity ToEntity(Game game)
        {
            return new GameEntity
            {
                ID = game.ID,
                Status = game.Status,
                MaxTurns = game.MaxTurns,
                CurrentTurn = game.CurrentTurn,
                CurrentPlayerIndex = game.CurrentPlayerIndex,
                Players = game.Players.Select(PlayerMapper.ToEntity).ToList()
            };
        }

        public static Game ToBusiness(GameEntity entity)
        {
            return new Game
            {
                ID = entity.ID,
                Status = entity.Status,
                MaxTurns = entity.MaxTurns,
                CurrentTurn = entity.CurrentTurn,
                CurrentPlayerIndex = entity.CurrentPlayerIndex,
                Players = entity.Players.Select(PlayerMapper.ToBusiness).ToList()
            };
        }
    }
}
