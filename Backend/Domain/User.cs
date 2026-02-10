using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class User
    {
        public int ID { get; set; }
        public string Username { get; set; }
        public int Points{ get; set; }

        public void UpdatePoints(int points)
        {
            Points += points;
        }
    }
}
