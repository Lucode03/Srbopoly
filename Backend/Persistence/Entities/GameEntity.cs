using System.Text.Json.Serialization;
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
        public List<PlayerEntity> Players { get; set; } = new List<PlayerEntity>();
        public BoardEntity Board { get; set; } = null!;
        // public List<CardEntity> RewardCardsDeck { get; set; } = new List<CardEntity>();
        // public List<CardEntity> SurpriseCardsDeck { get; set; } = new List<CardEntity>();
        
        public List<int> RewardCardsDeckIds { get; set; } = new List<int>();
        public List<int> SurpriseCardsDeckIds { get; set; } = new List<int>();
    }
}
