using Backend.Domain;

namespace Backend.Persistence.Entities
{
    public class PlayerEntity
    {
        public int ID {get; set;}
        public int Balance { get; set; }
        public int Position { get; set; }
        public Color Color { get; set; }
        public bool IsInJail { get; set; }
        public List<PropertyFieldEntity>? Properties { get; set; }
        public UserEntity User {get; set;}
    }
}
