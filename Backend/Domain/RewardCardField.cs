using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class RewardCardField : CardField
    {
        public List<RewardCard> Cards { get; set;}
        public override void Action(Player player)
        {

        }
        public override Card DrawCard()
        {
            return Cards.FirstOrDefault();
        }
    }
}
