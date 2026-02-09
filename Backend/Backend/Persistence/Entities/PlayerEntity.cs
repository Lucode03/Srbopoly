using Backend.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Persistence.Entities
{
    public class PlayerEntity : UserEntity
    {
        public int Balance { get; set; }
        public int Position { get; set; }
        public Color Color { get; set; }
        public bool IsInJail { get; set; }
        public ICollection<PropertyFieldEntity> Properties { get; set; }
    }
}
