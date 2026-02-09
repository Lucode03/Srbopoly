using Backend.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Persistence.Entities
{
    public class PropertyFieldEntity : FieldEntity
    {
        public PlayerEntity Owner { get; set; }
        public int Houses { get; set; }
        public int Hotels { get; set; }
    }
}
