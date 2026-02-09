using Backend.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Persistence.Entities
{
    public class GameEntity
    {
        public int ID { get; set; }
        public GameStatus Status { get; set; }
        public int MaxTurns { get; set; }
        public int CurrentTurn { get; set; }
        public int CurrentPlayerIndex { get; set; }
        public List<PlayerEntity> Players { get; set; }
        public BoardEntity Board { get; set; }
        // public DeckRewardCardsOrderEntity RewardsDeck {get; set;}
        // public DeckSurpriseCardsOrderEntity SurprisesDeck {get; set;}
    }
}
