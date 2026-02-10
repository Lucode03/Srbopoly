using Backend.Domain;
using Backend.Factories;
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
                Players = game.Players.Select(PlayerMapper.ToEntity).ToList(),
                Board = BoardMapper.ToEntity(game.GameBoard),
                RewardCardsDeck = game.RewardCardsDeck.Select(CardMapper.ToEntity).ToList(),
                SurpriseCardsDeck = game.SurpriseCardsDeck.Select(CardMapper.ToEntity).ToList()
            };
        }

        public static Game ToBusiness(GameEntity entity)
        {
            List<Player> players = entity.Players.Select(PlayerMapper.ToBusiness).ToList();

            return new Game
            {
                ID = entity.ID,
                Status = entity.Status,
                MaxTurns = entity.MaxTurns,
                CurrentTurn = entity.CurrentTurn,
                CurrentPlayerIndex = entity.CurrentPlayerIndex,
                Players = players,
                GameBoard = BoardMapper.ToBusiness(entity.Board,players),
                RewardCardsDeck = entity.RewardCardsDeck
                    .Select(card => CardFactory.CreateCard(card.GameCardID))
                    .OfType<RewardCard>()
                    .ToList(),

                SurpriseCardsDeck = entity.SurpriseCardsDeck
                    .Select(card => CardFactory.CreateCard(card.GameCardID))
                    .OfType<SurpriseCard>()
                    .ToList()       
            };
        }
    }
}
