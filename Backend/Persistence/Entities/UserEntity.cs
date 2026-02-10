using System.Text.Json.Serialization;

namespace Backend.Persistence.Entities
{
    [Index(nameof(Username), IsUnique = true)]
    public class UserEntity
    {
        [Key]
        public int ID { get; set; }
        [Required]
        [MaxLength(20)]
        public string Username { get; set; } = String.Empty;
        public int Points { get; set; }

        [JsonIgnore]
        public ICollection<PlayerEntity> Players { get; set; } = new List<PlayerEntity>();
    }
}
