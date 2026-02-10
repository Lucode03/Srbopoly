using Backend.Persistence.Entities;
using Backend.Persistence.DTO;
using Backend.Domain;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class GameController : ControllerBase
{
    public SrbopolyContext Context { get; set; }

    public GameController(SrbopolyContext context)
    {
        Context = context;
    }

    [HttpPost("CreateGame")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<GameDto>> CreateGame([FromBody] int maxTurns)
    {
        try
        {
            var game = new GameEntity
            {
                Status = GameStatus.Paused,
                MaxTurns = maxTurns,
                CurrentTurn = 0,
                CurrentPlayerIndex = 0,
                Board = new BoardEntity(),
            };

            await Context.Games.AddAsync(game);
            await Context.SaveChangesAsync();

            var dto = new GameDto
            {
                Id = game.ID,
                Status = game.Status,
                MaxTurns = game.MaxTurns,
                CurrentTurn = game.CurrentTurn,
                CurrentPlayerIndex = game.CurrentPlayerIndex,
                Board = new BoardDto
                {
                    Id = game.Board.ID,
                    PropertyFields = new List<PropertyFieldDto>()
                },
                Players = new List<PlayerDto>(),
                RewardCardsDeckIds = new List<int>(),
                SurpriseCardsDeckIds = new List<int>()
            };

            return Ok(dto);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpGet("GetById/{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<GameDto>> GetById(int id)
    {
        try
        {
            var game = await Context.Games
                .Include(g => g.Players)
                    .ThenInclude(p => p.User)
                .Include(g => g.Board)
                    .ThenInclude(b => b.PropertyFields)
                .Include(g => g.RewardCardsDeck)
                .Include(g => g.SurpriseCardsDeck)
                .FirstOrDefaultAsync(g => g.ID == id);

            if (game == null)
                return NotFound($"Game sa ID {id} nije pronađen.");

            var dto = new GameDto
            {
                Id = game.ID,
                Status = game.Status,
                MaxTurns = game.MaxTurns,
                CurrentTurn = game.CurrentTurn,
                CurrentPlayerIndex = game.CurrentPlayerIndex,
                Players = game.Players.Select(p => new PlayerDto
                {
                    Id = p.ID,
                    Username = p.User.Username,
                    Balance = p.Balance,
                    Position = p.Position,
                    Color = p.Color,
                    IsInJail = p.IsInJail
                }).ToList(),
                Board = new BoardDto
                {
                    Id = game.Board.ID,
                    PropertyFields = game.Board.PropertyFields?.Select(f => new PropertyFieldDto
                    {
                        Id = f.ID,
                        GameFieldID = f.GameFieldID,
                        OwnerId = f.OwnerID,
                        Houses = f.Houses,
                        Hotels = f.Hotels
                    }).ToList() ?? new List<PropertyFieldDto>()
                },
                RewardCardsDeckIds = game.RewardCardsDeck.Select(c => c.ID).ToList(),
                SurpriseCardsDeckIds = game.SurpriseCardsDeck.Select(c => c.ID).ToList()
            };

            return Ok(dto);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpGet("GetAll")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<List<GameDto>>> GetAll()
    {
        try
        {
            var games = await Context.Games
                .Include(g => g.Players)
                    .ThenInclude(p => p.User)
                .Include(g => g.Board)
                    .ThenInclude(b => b.PropertyFields)
                .Include(g => g.RewardCardsDeck)
                .Include(g => g.SurpriseCardsDeck)
                .ToListAsync();

            var dtos = games.Select(game => new GameDto
            {
                Id = game.ID,
                Status = game.Status,
                MaxTurns = game.MaxTurns,
                CurrentTurn = game.CurrentTurn,
                CurrentPlayerIndex = game.CurrentPlayerIndex,
                Players = game.Players.Select(p => new PlayerDto
                {
                    Id = p.ID,
                    Username = p.User.Username,
                    Balance = p.Balance,
                    Position = p.Position,
                    Color = p.Color,
                    IsInJail = p.IsInJail
                }).ToList(),
                Board = new BoardDto
                {
                    Id = game.Board.ID,
                    PropertyFields = game.Board.PropertyFields?.Select(f => new PropertyFieldDto
                    {
                        Id = f.ID,
                        GameFieldID = f.GameFieldID,
                        OwnerId = f.OwnerID,
                        Houses = f.Houses,
                        Hotels = f.Hotels
                    }).ToList() ?? new List<PropertyFieldDto>()
                },
                RewardCardsDeckIds = game.RewardCardsDeck.Select(c => c.ID).ToList(),
                SurpriseCardsDeckIds = game.SurpriseCardsDeck.Select(c => c.ID).ToList()
            }).ToList();

            return Ok(dtos);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpDelete("Delete/{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult> Delete(int id)
    {
        try
        {
            var game = await Context.Games.FindAsync(id);

            if (game == null)
                return NotFound($"Game sa ID {id} nije pronađen.");

            Context.Games.Remove(game);
            await Context.SaveChangesAsync();

            return Ok($"Game sa ID {id} je uspešno obrisan.");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}