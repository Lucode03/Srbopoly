using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Communications
{
    public partial class StartForm : Form
    {
        public StartForm()
        {
            InitializeComponent();
        }

        private void btnStartGame_Click(object sender, EventArgs e)
        {
            //pribavljanje imena iz baze i gameid iz baze

            int numUsers = 4;
            for(int i= 0;i<numUsers;i++)
            {
                ChatForm chatForm = new ChatForm("user"+i, "game");
                chatForm.Show();
            }
        }
    }
}
