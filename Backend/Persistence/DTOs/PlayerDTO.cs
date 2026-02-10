using Backend.Domain;

public class PlayerDto
{
    public int Id { get; set; }
    public string Username { get; set; } = string.Empty;
    public int Balance { get; set; }
    public int Position { get; set; }
    public Color Color { get; set; }
    public bool IsInJail { get; set; }
}