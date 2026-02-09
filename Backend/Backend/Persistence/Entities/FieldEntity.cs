using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Persistence.Entities
{
    public class FieldEntity
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public string FieldType { get; set; }
    }
}
