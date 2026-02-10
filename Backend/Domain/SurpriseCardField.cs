using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class SurpriseCardField : CardField
    {
        public List<SurpriseCard> Cards { get; set;}
        public override void Action(Player player)
        {

        }
        public override Card DrawCard(Game game)
        {
            return game.SurpriseCardsDeck.FirstOrDefault();
        }
    }
}
