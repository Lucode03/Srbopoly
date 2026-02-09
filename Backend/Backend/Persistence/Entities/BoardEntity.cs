using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Security.Permissions;
using System.Text;
using System.Threading.Tasks; 

namespace Backend.Persistence.Entities
{
    public class BoardEntity
    {
        [Key]
        public int ID { get; set; }
        public List<FieldEntity>? ChangeableFields { get; set; }
    }
}
