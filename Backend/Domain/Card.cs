using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public abstract class Card
    {
        public String? CardName { get; set; }
        public String? Description { get; set; }
        public int GameCardID {get; set;}
        public abstract void Apply(Player player);
    }
}
