using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class PropertyField : Field
    {
        public int Price { get; set; }
        public Player Owner { get; set; } = null;
        public int BaseRent { get; set; }
        public int Houses { get; set; } = 0;
        public int Hotels { get; set; } = 0;
        public String Type { get; set; }
        public override void Action(Player player)
        {
            
        }
        public void Buy(Player player)
        {
            if (Owner == null) 
                Owner = player;
            player.Pay(Price);
        }
        public int CalculateRent(Player player)
        {
            int rent=0;
            //racunanje cene placanja
            return rent;
        }
        public bool BuildHouse(Player player)
        {
            //logika za gradnju kuce
            return false;
        }
        public bool BuildHotel(Player player)
        {
            //logika za gradnju hotela
            return false;
        }
    }
}
