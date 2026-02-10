using Backend.Domain;
using Backend.Persistence.Entities;

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
            
                RewardCardsDeck = game.RewardCardsDeck.Select(CardMapper.ToEntity).ToList(),
                SurpriseCardsDeck = game.SurpriseCardsDeck.Select(CardMapper.ToEntity).ToList()
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
                
                RewardCardsDeck = entity.RewardCardsDeck
                    .Select(CardFactory.CreateCard)
                    .ToList();

                SurpriseCardsDeck = entity.SurpriseCardsDeck
                    .Select(CardFactory.CreateCard)
                    .ToList();           
            };
        }
    }
}
