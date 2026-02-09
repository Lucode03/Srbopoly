using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class BonusField : Field
    {
        public int Bonus { get; set; }

        public override void Action(Player player)
        {
            player.Receive(Bonus);
        }
    }
}
