using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Persistence.Entities
{
    public class CardFieldEntity : FieldEntity
    {
        public int CurrentCardIndex { get; set; }
    }
}
