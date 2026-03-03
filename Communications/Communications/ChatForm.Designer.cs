namespace Communications
{
    partial class ChatForm
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            txtMessage = new TextBox();
            lbxMessages = new ListBox();
            btnSend = new Button();
            SuspendLayout();
            // 
            // txtMessage
            // 
            txtMessage.Font = new Font("Segoe UI", 9.75F);
            txtMessage.Location = new Point(6, 491);
            txtMessage.Name = "txtMessage";
            txtMessage.Size = new Size(657, 25);
            txtMessage.TabIndex = 0;
            txtMessage.KeyDown += txtMessage_KeyDown;
            // 
            // lbxMessages
            // 
            lbxMessages.Font = new Font("Segoe UI", 9.75F);
            lbxMessages.FormattingEnabled = true;
            lbxMessages.ItemHeight = 17;
            lbxMessages.Location = new Point(6, 8);
            lbxMessages.Name = "lbxMessages";
            lbxMessages.Size = new Size(732, 463);
            lbxMessages.TabIndex = 1;
            // 
            // btnSend
            // 
            btnSend.Font = new Font("Segoe UI", 9.75F);
            btnSend.Location = new Point(669, 483);
            btnSend.Name = "btnSend";
            btnSend.Size = new Size(69, 36);
            btnSend.TabIndex = 2;
            btnSend.Text = "Send";
            btnSend.UseVisualStyleBackColor = true;
            btnSend.Click += btnSend_Click;
            // 
            // ChatForm
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(744, 531);
            Controls.Add(btnSend);
            Controls.Add(lbxMessages);
            Controls.Add(txtMessage);
            Name = "ChatForm";
            Text = "Chat";
            Load += ChatForm_Load;
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private TextBox txtMessage;
        private ListBox lbxMessages;
        private Button btnSend;
    }
}
