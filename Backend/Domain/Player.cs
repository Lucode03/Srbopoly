using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class Player:User
    {
        public int Balance { get; set; }
        public int Position { get; set; }
        public Color Color { get; set; }
        public bool IsInJail { get; set; }
        public List<PropertyField> Properties { get; set; } = new List<PropertyField>();

        public void Receive(int amount)
        {
            Balance += amount;
        }

        public void Pay(int amount)
        {
            Balance -= amount;
        }

        public void Move(int amount)
        {
            Position = (Position + amount) % 40;
        }

        public void GoToJail()
        {
            Position = 20;
        }
    }
}
