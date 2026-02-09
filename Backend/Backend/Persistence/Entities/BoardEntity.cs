using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Persistence.Entities
{
    public class BoardEntity
    {
        public int ID { get; set; }
        public ICollection<FieldEntity> Fields { get; set; }
    }
}
