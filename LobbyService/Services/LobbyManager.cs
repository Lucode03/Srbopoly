namespace LobbyService.Services;

public class LobbyManager
{
    private readonly GameCodeService _codeService;
    
    // Naš "Auto-increment" brojač za Lobby ID
    private int _lobbyIdCounter = 0;
    // Naš "Auto-increment" brojač za Player ID
    private int _playerIdCounter = 0;

    // Glavno skladište: Key = AccessCode, Value = LobbyDTO
    private readonly ConcurrentDictionary<string, LobbyDTO> _lobbies = new();

    public LobbyManager(GameCodeService codeService)
    {
        _codeService = codeService;
    }

    public LobbyDTO CreateLobby(int hostUserId)
    {
        // 1. Atomski uvećaj ID (kao auto-increment u SQL-u)
        int newId = Interlocked.Increment(ref _lobbyIdCounter);

        // 2. Generiši kod na osnovu tog ID-a koristeći tvoj servis
        string accessCode = _codeService.EncodeGameId(newId);

        var newLobby = new LobbyDTO
        {
            Id = newId,
            HostUserId = hostUserId,
            AccessCode = accessCode,
            Players = new List<LobbyPlayerDTO>()
        };

        _lobbies.TryAdd(accessCode, newLobby);
        return newLobby;
    }

    public LobbyDTO? GetLobby(string accessCode)
    {
        _lobbies.TryGetValue(accessCode, out var lobby);
        return lobby;
    }

    public LobbyPlayerDTO AddPlayer(string accessCode, int userId, string username)
    {
        if (!_lobbies.TryGetValue(accessCode, out var lobby))
            throw new KeyNotFoundException("Lobi nije pronađen.");

        if (lobby.Players.Count >= 4)
            throw new InvalidOperationException("Lobi je pun.");

        if (lobby.Players.Any(p => p.UserId == userId))
            throw new InvalidOperationException("Igrač je već u lobiju.");

        // Automatska dodela boje
        var takenColors = lobby.Players.Select(p => p.Color).ToList();
        var assignedColor = GetFirstAvailableColor(takenColors);

        var newPlayer = new LobbyPlayerDTO
        {
            Id = Interlocked.Increment(ref _playerIdCounter),
            UserId = userId,
            Username = username,
            LobbyId = lobby.Id,
            Color = assignedColor,
            IsReady = false,
            RolledNumber = 0
        };

        lobby.Players.Add(newPlayer);
        return newPlayer;
    }

    public void RemovePlayer(string accessCode, int userId)
    {
        if (!_lobbies.TryGetValue(accessCode, out var lobby)) return;

        var player = lobby.Players.FirstOrDefault(p => p.UserId == userId);
        if (player == null) return;

        lobby.Players.Remove(player);

        // Auto-cleanup: Ako nema više igrača, obriši lobi
        if (lobby.Players.Count == 0)
        {
            _lobbies.TryRemove(accessCode, out _);
            return;
        }

        // Host Migration: Ako je host izašao, dodeli ulogu sledećem
        if (lobby.HostUserId == userId)
        {
            lobby.HostUserId = lobby.Players.First().UserId;
        }
    }

    private Color GetFirstAvailableColor(List<Color> takenColors)
    {
        foreach (Color color in Enum.GetValues(typeof(Color)))
        {
            if (!takenColors.Contains(color)) return color;
        }
        return Color.White; // Fallback