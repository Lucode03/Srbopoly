using Backend.Persistence.Entities;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class CardController : ControllerBase
{
    public SrbopolyContext Context { get; set; }

    public CardController(SrbopolyContext context)
    {
        Context = context;
    }

    [HttpPost("CreateCard")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<CardDto>> CreateCard(int gameId, [FromBody] CardDto dto)
    {
        try
        {
            var game = await Context.Games.FindAsync(gameId);
            if (game == null)
                return NotFound($"Game sa ID {gameId} nije pronađen.");

            if (dto.GameCardID < 0)
                return BadRequest("GameCardID mora biti pozitivan.");

            var card = new CardEntity
            {
                GameCardID = dto.GameCardID,
                GameId = gameId,
                Game = game
            };

            await Context.Cards.AddAsync(card);
            await Context.SaveChangesAsync();

            var resultDto = new CardDto
            {
                Id = card.ID,
                GameCardID = card.GameCardID,
            };

            return Ok(resultDto);
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
    public async Task<ActionResult<CardDto>> GetById(int id)
    {
        try
        {
            var card = await Context.Cards
                .Include(c => c.Game)
                .FirstOrDefaultAsync(c => c.ID == id);

            if (card == null)
                return NotFound($"Card sa ID {id} nije pronađena.");

            var dto = new CardDto
            {
                Id = card.ID,
                GameCardID = card.GameCardID,
            };

            return Ok(dto);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpGet("GetByGame/{gameId}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<List<CardDto>>> GetByGame(int gameId)
    {
        try
        {
            var game = await Context.Games.FindAsync(gameId);
            if (game == null)
                return NotFound($"Game sa ID {gameId} nije pronađen.");

            var cards = await Context.Cards
                .Where(c => c.GameId == gameId)
                .ToListAsync();

            var dtos = cards.Select(c => new CardDto
            {
                Id = c.ID,
                GameCardID = c.GameCardID,
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
            var card = await Context.Cards.FindAsync(id);
            if (card == null)
                return NotFound($"Card sa ID {id} nije pronađena.");

            Context.Cards.Remove(card);
            await Context.SaveChangesAsync();

            return Ok($"Card sa ID {id} je uspešno obrisana.");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}