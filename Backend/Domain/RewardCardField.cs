using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class RewardCardField : CardField
    {
        public override void Action(Player player)
        {

        }
        public override Card DrawCard(Game game)
        {
            return game.RewardCardsDeck.FirstOrDefault();
        }
    }
}
