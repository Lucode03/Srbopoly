using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public abstract class Field
    {
        public string? Name { get; set; }
        public int GameFieldID {get; set;}

        public abstract void Action(Player player);
    }
}
