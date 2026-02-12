using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace Backend.Persistence.Entities
{
    [NotMapped]
    public class CardEntity
    {
        [Key]
        public int ID {get; set;}
        public int GameCardID {get; set;}

        public int GameId { get; set; }

        [JsonIgnore]
        public GameEntity Game { get; set; } = null!;
    }
}