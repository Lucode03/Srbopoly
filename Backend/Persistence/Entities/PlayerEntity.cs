using Backend.Domain;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace Backend.Persistence.Entities
{
    public class PlayerEntity
    {
        [Key]
        public int ID {get; set;}
        [Required]
        public int Balance { get; set; }
        [Required]
        public int Position { get; set; }
        [Required]
        public Color Color { get; set; }
        public bool IsInJail { get; set; }
        [ForeignKey(nameof(User))]
        public int UserId { get; set; }
        public UserEntity User {get; set;} = null!;

        [ForeignKey(nameof(Game))]
        public int GameId { get; set; }
        [JsonIgnore]
        public GameEntity Game { get; set; } = null!;

        [JsonIgnore]
        public List<PropertyFieldEntity> OwnedFields { get; set; } = new List<PropertyFieldEntity>();
    }
}
