using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Backend.Domain
{
    public class Game
    {
        public int ID {  get; set; }
        public GameStatus Status { get; set; }
        public int MaxTurns { get; set; }
        public int CurrentTurn { get; set; }
        public int CurrentPlayerIndex { get; set; }
        public List<Player> Players { get; set; }
        public Board GameBoard { get; set; }
        public List<RewardCard> RewardCardsDeck { get; set; }
        public List<SurpriseCard> SurpriseCardsDeck { get; set; }

        public void StartGame() 
        {
            Status=GameStatus.Running;
        }
        public void PauseGame()
        {
            Status = GameStatus.Paused;
        }
        public void EndGame()
        {
            Status = GameStatus.Finished;
        }
        public void CheckWinner()
        {
            //provera podednika
        }
        public void NextTurn()
        {
            if (CurrentTurn < MaxTurns)
            {
                CurrentTurn++;
            }
            else
            {
                CheckWinner();
                EndGame();
            }
        }
        public void NextPlayer()
        {
            CurrentPlayerIndex=(CurrentPlayerIndex+1)%Players.Count;
        }
        public void AddPlayer(Player player)
        {
            if (!Players.Contains(player) && Players.Count<4)
                Players.Add(player);
        }
        public void SetMaxTurns(int maxTurns)
        {
            MaxTurns = maxTurns;
        }
    }
}
