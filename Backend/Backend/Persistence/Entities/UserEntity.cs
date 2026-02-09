using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Persistence.Entities
{
    public class UserEntity
    {
        public int ID { get; set; }
        public string Username { get; set; }
        public int Points { get; set; }
    }
}
