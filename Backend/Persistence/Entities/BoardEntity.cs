using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;


namespace Backend.Persistence.Entities;

public class BoardEntity
{
    [Key]
    public int ID { get; set; }
    [ForeignKey(nameof(Game))]
    public int GameId { get; set; }

    [JsonIgnore]
    public GameEntity Game { get; set; } = null!;
    public List<PropertyFieldEntity>? PropertyFields { get; set; }
}
