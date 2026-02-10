using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public abstract class CardField : Field
    {
        public abstract override void Action(Player player);
        public abstract Card DrawCard(Game game);
    }
}
