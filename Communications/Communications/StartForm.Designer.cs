namespace Communications
{
    partial class StartForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
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
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            btnStartGame = new Button();
            numUserID = new NumericUpDown();
            numGameID = new NumericUpDown();
            ((System.ComponentModel.ISupportInitialize)numUserID).BeginInit();
            ((System.ComponentModel.ISupportInitialize)numGameID).BeginInit();
            SuspendLayout();
            // 
            // btnStartGame
            // 
            btnStartGame.Font = new Font("Segoe UI", 9.75F);
            btnStartGame.Location = new Point(176, 186);
            btnStartGame.Name = "btnStartGame";
            btnStartGame.Size = new Size(88, 34);
            btnStartGame.TabIndex = 1;
            btnStartGame.Text = "Start game";
            btnStartGame.UseVisualStyleBackColor = true;
            btnStartGame.Click += btnStartGame_Click;
            // 
            // numUserID
            // 
            numUserID.Location = new Point(60, 65);
            numUserID.Maximum = new decimal(new int[] { 1000, 0, 0, 0 });
            numUserID.Name = "numUserID";
            numUserID.Size = new Size(100, 23);
            numUserID.TabIndex = 3;
            // 
            // numGameID
            // 
            numGameID.Location = new Point(210, 65);
            numGameID.Maximum = new decimal(new int[] { 1000, 0, 0, 0 });
            numGameID.Name = "numGameID";
            numGameID.Size = new Size(120, 23);
            numGameID.TabIndex = 4;
            // 
            // StartForm
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(492, 450);
            Controls.Add(numGameID);
            Controls.Add(numUserID);
            Controls.Add(btnStartGame);
            Name = "StartForm";
            Text = "StartForm";
            ((System.ComponentModel.ISupportInitialize)numUserID).EndInit();
            ((System.ComponentModel.ISupportInitialize)numGameID).EndInit();
            ResumeLayout(false);
        }

        #endregion
        private Button btnStartGame;
        private NumericUpDown numUserID;
        private NumericUpDown numGameID;
    }
}