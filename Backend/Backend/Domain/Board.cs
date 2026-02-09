using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class Board
    {
        public int Size {get; set;} = 40;
        public List<Field> Fields { get; set; }

        public Field GetField(int position)
        {
            return Fields[position % Size];
        }
    }
}
