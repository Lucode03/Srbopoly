using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Persistence.Entities
{
    public class UserEntity
    {
        [Key]
        public int ID { get; set; }
        [MaxLength(20)]
        public string Username { get; set; }
        public int Points { get; set; }
    }
}
