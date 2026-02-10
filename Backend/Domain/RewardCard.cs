using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class RewardCard : Card
    {
        public int Reward {  get; set; }
        public override void Apply(Player player)
        {
            player.Receive(Reward);
        }
    }
}
