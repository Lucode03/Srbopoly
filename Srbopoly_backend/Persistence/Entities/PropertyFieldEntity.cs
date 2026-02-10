namespace Backend.Persistence.Entities
{
    public class PropertyFieldEntity : FieldEntity
    {
        public PlayerEntity? Owner { get; set; }
        public int Houses { get; set; }
        public int Hotels { get; set; }
    }
}
