using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;
using System.Text.Json;
namespace Communications
{
    public partial class ChatForm : Form
    {
        private IConnection connection;
        private IChannel channel;
        private string exchangeName;
        private string queueName;
        private string username;

        public ChatForm(string u, string e)
        {
            InitializeComponent();

            btnSend.Enabled = false;
            username = u;
            exchangeName = e;
            queueName = $"chat_{exchangeName}_{username}";

            Load += ChatForm_Load;
        }
        private async void ChatForm_Load(object sender, EventArgs e)
        {
            if (!ChatSessions.Channels.ContainsKey(username))
            {
                InitRabbitMQ();
                ChatSessions.Channels[username] = channel;
            }
            else
            {
                channel = ChatSessions.Channels[username];
            }
            btnSend.Enabled = true;
        }
        private async void InitRabbitMQ()
        {
            var factory = new ConnectionFactory { HostName = "localhost" };
            connection = await factory.CreateConnectionAsync();
            channel = await connection.CreateChannelAsync();

            await channel.ExchangeDeclareAsync(exchange: exchangeName,
                                              type: ExchangeType.Fanout,
                                              durable: true);

            await channel.QueueDeclareAsync(queue: queueName,
                                            durable: true,
                                            exclusive: false,
                                            autoDelete: false);



            await channel.QueueBindAsync(queue: queueName,
                                        exchange: exchangeName,
                                        routingKey: "");

            // Consumer
            var consumer = new AsyncEventingBasicConsumer(channel);
            consumer.ReceivedAsync += Consumer_ReceivedAsync;

            await channel.BasicConsumeAsync(queue: queueName,
                                            autoAck: false,
                                            consumer: consumer);

        }
        private async Task Consumer_ReceivedAsync(object sender, BasicDeliverEventArgs e)
        {
            var body = e.Body.ToArray();
            string json = Encoding.UTF8.GetString(body);

            var message = JsonSerializer.Deserialize<ChatMessage>(json);

            // Invoke da update UI thread
            if (lbxMessages.InvokeRequired)
            {
                lbxMessages.Invoke(() => AddMessage(message));
            }
            else
            {
                AddMessage(message);
            }

            await channel.BasicAckAsync(e.DeliveryTag, false);
        }
        private void AddMessage(ChatMessage message)
        {
            if (message.User == username)
            {
                lbxMessages.Items.Add($"Me: {message.Text}");
            }
            else
            {
                lbxMessages.Items.Add($"{message.User}: {message.Text}");
            }
            lbxMessages.TopIndex = lbxMessages.Items.Count - 1;
        }

        private async void btnSend_Click(object sender, EventArgs e)
        {
            string text = txtMessage.Text.Trim();
            if (string.IsNullOrEmpty(text)) return;

            var chatMessage = new ChatMessage
            {
                User = username,
                Text = text
            };


            string json = JsonSerializer.Serialize(chatMessage);
            var body = Encoding.UTF8.GetBytes(json);

            var props = new BasicProperties
            {
                Persistent = true
            };

            await channel.BasicPublishAsync(exchange: exchangeName,
                                            routingKey: "",
                                            mandatory: false,
                                            basicProperties: props,
                                            body: body);

            txtMessage.Clear();
        }
        private void txtMessage_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                btnSend.PerformClick();
                e.SuppressKeyPress = true;
            }
        }    
    }
}
