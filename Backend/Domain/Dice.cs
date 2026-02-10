using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class Dice
    {
        private static readonly Random _random = new Random();

        public int Roll()
        {
            return _random.Next(1, 7);
        }
    }
}
