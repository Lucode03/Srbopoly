using Backend.Persistence.Entities;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class BoardController : ControllerBase
{
    public SrbopolyContext Context { get; set; }

    public BoardController(SrbopolyContext context)
    {
        Context = context;
    }

    [HttpPost("CreateBoard")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<BoardDto>> CreateBoard(int gameId)
    {
        try
        {
            var game = await Context.Games.FindAsync(gameId);
            if (game == null)
                return NotFound($"Game sa ID {gameId} ne postoji.");

            var board = new BoardEntity
            {
                GameId = gameId,
                PropertyFields = new List<PropertyFieldEntity>()
            };

            await Context.Boards.AddAsync(board);
            await Context.SaveChangesAsync();

            var dto = new BoardDto
            {
                Id = board.ID,
                PropertyFields = new List<PropertyFieldDto>()
            };

            return Ok(dto);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpGet("GetBoardByGame/{gameId}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<BoardDto>> GetBoardByGame(int gameId)
    {
        try
        {
            var board = await Context.Boards
                .Include(b => b.PropertyFields)
                .FirstOrDefaultAsync(b => b.GameId == gameId);

            if (board == null)
                return NotFound($"Board za Game ID {gameId} nije pronađen.");

            var dto = new BoardDto
            {
                Id = board.ID,
                PropertyFields = board.PropertyFields != null 
                    ? board.PropertyFields.Select(f => new PropertyFieldDto
                    {
                        Id = f.ID,
                        GameFieldID = f.GameFieldID,
                        OwnerId = f.OwnerID,
                        Houses = f.Houses,
                        Hotels = f.Hotels
                    }).ToList()
                    : new List<PropertyFieldDto>()
            };

            return Ok(dto);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpPost("AddPropertyField")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<PropertyFieldDto>> AddPropertyField(int boardId, PropertyFieldDto fieldDto)
    {
        try
        {
            var board = await Context.Boards.FindAsync(boardId);
            if (board == null)
                return NotFound($"Board sa ID {boardId} nije pronađen.");

            var field = new PropertyFieldEntity
            {
                GameFieldID = fieldDto.GameFieldID,
                Houses = fieldDto.Houses,
                Hotels = fieldDto.Hotels,
                OwnerID = fieldDto.OwnerId,
            };

            board.PropertyFields ??= new List<PropertyFieldEntity>();
            board.PropertyFields.Add(field);

            await Context.SaveChangesAsync();

            var dto = new PropertyFieldDto
            {
                Id = field.ID,
                GameFieldID = field.GameFieldID,
                OwnerId = field.OwnerID,
                Houses = field.Houses,
                Hotels = field.Hotels
            };

            return Ok(dto);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}