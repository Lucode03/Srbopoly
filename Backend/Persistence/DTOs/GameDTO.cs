using Backend.Domain;

namespace Backend.Persistence.DTO;

public class GameDto
{
    public int Id { get; set; }
    public GameStatus Status { get; set; }
    public int MaxTurns { get; set; }
    public int CurrentTurn { get; set; }
    public int CurrentPlayerIndex { get; set; }

    public List<PlayerDto> Players { get; set; } = new();
    public BoardDto Board { get; set; } = null!;
    public List<int> RewardCardsDeckIds { get; set; } = new();
    public List<int> SurpriseCardsDeckIds { get; set; } = new();
}