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
        public String? Type { get; set; }
        //Position -> postavi igraca na polje Amount
        //Movement -> pomeriti igraca za Amount polja
        //Balance -> dodati igracu Amount para
        public override void Apply(Player player)
        {
            
        }
    }
}
