using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class PaymentField : Field
    {
        public int Price { get; set; }
        public override void Action(Player player)
        {
            player.Pay(Price);
        }

    }
}
