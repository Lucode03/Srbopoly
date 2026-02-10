using Backend.Domain;

namespace Backend.Persistence.Entities
{
    public class GameEntity
    {
        [Key]
        public int ID { get; set; }
        public GameStatus Status { get; set; }
        public int MaxTurns { get; set; }
        public int CurrentTurn { get; set; }
        public int CurrentPlayerIndex { get; set; }
        public List<PlayerEntity>? Players { get; set; }
        public BoardEntity? Board { get; set; }

        public List<CardEntity>? RewardCardsDeck { get; set; }
        public List<CardEntity>? SurpriseCardsDeck { get; set; }
    }
}
