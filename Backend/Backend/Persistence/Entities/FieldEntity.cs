using System.ComponentModel.DataAnnotations;

namespace Backend.Persistence.Entities
{
    public class FieldEntity
    {
        [Key]
        public int ID { get; set; }
        public string FieldType { get; set; }
        public int GameFieldID {get; set;}
    }
}
