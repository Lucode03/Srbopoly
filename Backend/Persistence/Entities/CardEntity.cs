namespace Backend.Persistence.Entities
{
    public class CardEntity
    {
        [Key]
        public int ID {get; set;}
        public String? CardType {get; set;}
        public int GameCardID {get; set;}
    }
}