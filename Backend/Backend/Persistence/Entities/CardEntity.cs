using System.ComponentModel.DataAnnotations;

namespace Backend.Persistence.Entities
{
    public class CardEntity
    {
        [Key]
        public int ID {get; set;}
        public int GameCardID {get; set;}
    }
}