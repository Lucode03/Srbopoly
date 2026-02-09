using System.ComponentModel.DataAnnotations;

namespace Backend.Persistence.Entities
{
    public class DeckSurpriseCardsOrderEntity
    {
        [Key]
        public int ID {get; set;}
        public List<CardEntity> Cards {get; set;}
    }
}
