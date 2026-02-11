using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.DTO;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class GameMapper
    {
        public static GameDto ToEntity(Game game)
        {
            return new GameDto
            {
                Id = game.ID,
                Status = game.Status,
                MaxTurns = game.MaxTurns,
                CurrentTurn = game.CurrentTurn,
                CurrentPlayerIndex = game.CurrentPlayerIndex,
                Players = game.Players.Select(PlayerMapper.ToDTO).ToList(),
                Board = BoardMapper.ToDTO(game.GameBoard),
                // RewardCardsDeck = game.RewardCardsDeck.Select(CardMapper.ToEntity).ToList(),
                // SurpriseCardsDeck = game.SurpriseCardsDeck.Select(CardMapper.ToEntity).ToList()
                RewardCardsDeckIds = game.RewardCardsDeck.Select(c => c.GameCardID).ToList(),
                SurpriseCardsDeckIds = game.SurpriseCardsDeck.Select(c => c.GameCardID).ToList()
            };
        }

        public static Game ToBusiness(GameDto dto)
        {
            List<Player> players = dto.Players.Select(PlayerMapper.ToBusiness).ToList();

            return new Game
            {
                ID = dto.Id,
                Status = dto.Status,
                MaxTurns = dto.MaxTurns,
                CurrentTurn = dto.CurrentTurn,
                CurrentPlayerIndex = dto.CurrentPlayerIndex,
                Players = players,
                GameBoard = BoardMapper.ToBusiness(dto.Board,players),
                // RewardCardsDeck = entity.RewardCardsDeck
                //     .Select(card => CardFactory.CreateCard(card.GameCardID))
                //     .OfType<RewardCard>()
                //     .ToList(),

                // SurpriseCardsDeck = entity.SurpriseCardsDeck
                //     .Select(card => CardFactory.CreateCard(card.GameCardID))
                //     .OfType<SurpriseCard>()
                //     .ToList()     
                RewardCardsDeck = dto.RewardCardsDeckIds
                    .Select(c => CardFactory.CreateCard(c))
                    .OfType<RewardCard>()
                    .ToList(),

                SurpriseCardsDeck = dto.SurpriseCardsDeckIds
                    .Select(c => CardFactory.CreateCard(c))
                    .OfType<SurpriseCard>()
                    .ToList()   
            };
        }
    }
}
