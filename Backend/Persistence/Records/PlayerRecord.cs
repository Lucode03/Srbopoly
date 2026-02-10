namespace Backend.Persistence.Records;
using Backend.Domain;

public record CreatePlayerRequest(
    int UserId,
    int GameId,
    int Balance,
    int Position,
    Color Color,
    bool IsInJail
);