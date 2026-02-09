namespace Backend.Persistence.Entities
{
    public class DeckRewardCardsOrderEntity
    {
        [Key]
        public int ID {get; set;}
        public List<CardEntity?> Cards {get; set;}
    }
}
