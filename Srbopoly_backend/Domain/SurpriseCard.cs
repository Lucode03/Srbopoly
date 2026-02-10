using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class SurpriseCard : Card
    {
        public int Amount { get; set; }
        public int Type { get; set; }
        public override void Apply(Player player)
        {
            
        }
    }
}
