using Backend.Persistence.Entities;
using Backend.Persistence.Records;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class PlayerController : ControllerBase
{
    public SrbopolyContext Context { get; set; }

    public PlayerController(SrbopolyContext context)
    {
        Context = context;
    }

    [HttpPost("CreatePlayer")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult> CreatePlayer([FromBody] CreatePlayerRequest request)
    {
        try
        {
            var user = await Context.Users.FindAsync(request.UserId);
            if (user == null)
                return NotFound($"User sa ID {request.UserId} ne postoji.");

            var game = await Context.Games.FindAsync(request.GameId);
                    if (game == null)
            return NotFound($"Game sa ID {request.GameId} ne postoji.");

            if (request.Balance < 0)
                return BadRequest("Balance ne može biti negativan.");

            if (request.Position < 0)
                return BadRequest("Position ne može biti negativna.");

            var player = new PlayerEntity
            {
                UserId = request.UserId,
                Balance = request.Balance,
                Position = request.Position,
                Color = request.Color,
                IsInJail = request.IsInJail
            };

            await Context.Players.AddAsync(player);
            await Context.SaveChangesAsync();

            var dto = new PlayerDto
            {
                Id = player.ID,
                Username = user.Username,
                Balance = player.Balance,
                Position = player.Position,
                Color = player.Color,
                IsInJail = player.IsInJail
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
    public async Task<ActionResult> GetById(int id)
    {
        try
        {
            var player = await Context.Players
                .Include(p => p.User)
                .FirstOrDefaultAsync(p => p.ID == id);

            if (player == null)
                return NotFound($"Player sa ID {id} nije pronađen.");

            var dto = new PlayerDto
            {
                Id = player.ID,
                Username = player.User.Username,
                Balance = player.Balance,
                Position = player.Position,
                Color = player.Color,
                IsInJail = player.IsInJail
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
    public async Task<ActionResult> GetAll()
    {
        try
        {
            var players = await Context.Players
                .Include(p => p.User)
                .ToListAsync();

            var dtos = players.Select(p => new PlayerDto
            {
                Id = p.ID,
                Username = p.User.Username,
                Balance = p.Balance,
                Position = p.Position,
                Color = p.Color,
                IsInJail = p.IsInJail,
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
            var player = await Context.Players.FindAsync(id);

            if (player == null)
                return NotFound($"Player sa ID {id} nije pronađen.");

            Context.Players.Remove(player);
            await Context.SaveChangesAsync();

            return Ok($"Player sa ID {id} je uspešno obrisan.");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}