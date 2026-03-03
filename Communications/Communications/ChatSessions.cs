using RabbitMQ.Client;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Communications
{
    public class ChatSessions
    {
        public static Dictionary<string, IChannel> Channels = new();
    }
}
