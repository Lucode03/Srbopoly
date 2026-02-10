namespace Backend.Persistence.Entities
{
    public class PropertyFieldEntity
    {
        [Key]
        public int ID { get; set; }
        public int GameFieldID { get; set; }

        [ForeignKey("Owner")]
        public int? OwnerID { get; set; }
        public PlayerEntity? Owner { get; set; }
        public int Houses { get; set; }
        public int Hotels { get; set; }
    }
}
