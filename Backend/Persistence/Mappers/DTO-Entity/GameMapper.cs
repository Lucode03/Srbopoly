using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.DTO;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class GameMapper
    {
        public static GameDto ToDto(GameEntity entity)
        {
            return new GameDto
            {
                Id = entity.ID,
                Status = entity.Status,
                MaxTurns = entity.MaxTurns,
                CurrentTurn = entity.CurrentTurn,
                CurrentPlayerIndex = entity.CurrentPlayerIndex,

                Players = entity.Players?
                    .Select(PlayerMapper.ToDto)
                    .ToList() ?? new(),

                Board = BoardMapper.ToDto(entity.Board),

                RewardCardsDeckIds = entity.RewardCardsDeckIds?.ToList() ?? new(),
                SurpriseCardsDeckIds = entity.SurpriseCardsDeckIds?.ToList() ?? new()
            };
        }
        public static GameEntity ToEntity(GameDto dto)
    {
        var entity = new GameEntity
        {
            ID = dto.Id,
            Status = dto.Status,
            MaxTurns = dto.MaxTurns,
            CurrentTurn = dto.CurrentTurn,
            CurrentPlayerIndex = dto.CurrentPlayerIndex,

            Players = dto.Players?
                .Select(PlayerMapper.ToEntity)
                .ToList() ?? new(),

            RewardCardsDeckIds = dto.RewardCardsDeckIds?.ToList() ?? new(),
            SurpriseCardsDeckIds = dto.SurpriseCardsDeckIds?.ToList() ?? new()
        };

        // Board mapping (FK fix ako treba)
        entity.Board = BoardMapper.ToEntity(dto.Board, entity.ID);

        return entity;
    }
    }
}
