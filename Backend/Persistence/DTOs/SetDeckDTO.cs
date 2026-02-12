public class SetDeckDto
{
    public int DeckType { get; set; }
    public List<int> CardsOrder { get; set; } = new();
}