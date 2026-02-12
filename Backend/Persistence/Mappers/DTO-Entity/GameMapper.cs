using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.DTO;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class GameMapperDE
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
                    .Select(PlayerMapperDE.ToDto)
                    .ToList() ?? new(),

                Board = BoardMapperDE.ToDto(entity.Board),

                RewardCardsDeckIds = entity.RewardCardsDeckIds?.ToList() ?? new List<int>(),
                SurpriseCardsDeckIds = entity.SurpriseCardsDeckIds?.ToList() ?? new List<int>()
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
                    .Select(PlayerMapperDE.ToEntity)
                    .ToList() ?? new List<PlayerEntity>(),

                RewardCardsDeckIds = dto.RewardCardsDeckIds?.ToList() ?? new List<int>(),
                SurpriseCardsDeckIds = dto.SurpriseCardsDeckIds?.ToList() ?? new List<int>()
            };

            if (dto.Board != null)
            {
                entity.Board = BoardMapperDE.ToEntity(dto.Board, entity.ID);
            }

            return entity;
        }
    }
}
