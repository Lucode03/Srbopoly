namespace Backend.Persistence.Entities
{
    public class BoardEntity
    {
        [Key]
        public int ID { get; set; }
        public List<PropertyFieldEntity>? PropertyFields { get; set; }
    }
}
