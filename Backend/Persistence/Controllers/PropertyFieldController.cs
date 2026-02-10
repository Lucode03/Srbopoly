using Backend.Persistence.Entities;
namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class PropertyFieldController : ControllerBase
{
    public SrbopolyContext Context { get; set; }

    public PropertyFieldController(SrbopolyContext context)
    {
        Context = context;
    }

    [HttpPost("CreatePropertyField")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<PropertyFieldDto>> CreatePropertyField(int boardId, [FromBody] PropertyFieldDto dto)
    {
        try
        {
            var board = await Context.Boards.FindAsync(boardId);
            if (board == null)
                return NotFound($"Board sa ID {boardId} nije pronađen.");

            var field = new PropertyFieldEntity
            {
                GameFieldID = dto.GameFieldID,
                OwnerID = dto.OwnerId,
                Houses = dto.Houses,
                Hotels = dto.Hotels,
                BoardId = boardId
            };

            await Context.PropertyFields.AddAsync(field);
            await Context.SaveChangesAsync();

            var resultDto = new PropertyFieldDto
            {
                Id = field.ID,
                GameFieldID = field.GameFieldID,
                OwnerId = field.OwnerID,
                Houses = field.Houses,
                Hotels = field.Hotels
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
    public async Task<ActionResult<PropertyFieldDto>> GetById(int id)
    {
        try
        {
            var field = await Context.PropertyFields.FindAsync(id);
            if (field == null)
                return NotFound($"PropertyField sa ID {id} nije pronađen.");

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

    [HttpGet("GetAllForBoard/{boardId}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<List<PropertyFieldDto>>> GetAllForBoard(int boardId)
    {
        try
        {
            var fields = await Context.PropertyFields
                .Where(f => f.BoardId == boardId)
                .ToListAsync();

            if (!fields.Any())
                return NotFound($"Nema property fieldova za Board ID {boardId}.");

            var dtos = fields.Select(f => new PropertyFieldDto
            {
                Id = f.ID,
                GameFieldID = f.GameFieldID,
                OwnerId = f.OwnerID,
                Houses = f.Houses,
                Hotels = f.Hotels
            }).ToList();

            return Ok(dtos);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }

    [HttpPut("Update/{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<PropertyFieldDto>> Update(int id, [FromBody] PropertyFieldDto dto)
    {
        try
        {
            var field = await Context.PropertyFields.FindAsync(id);
            if (field == null)
                return NotFound($"PropertyField sa ID {id} nije pronađen.");

            field.GameFieldID = dto.GameFieldID;
            field.OwnerID = dto.OwnerId;
            field.Houses = dto.Houses;
            field.Hotels = dto.Hotels;

            await Context.SaveChangesAsync();

            var resultDto = new PropertyFieldDto
            {
                Id = field.ID,
                GameFieldID = field.GameFieldID,
                OwnerId = field.OwnerID,
                Houses = field.Houses,
                Hotels = field.Hotels
            };

            return Ok(resultDto);
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
            var field = await Context.PropertyFields.FindAsync(id);
            if (field == null)
                return NotFound($"PropertyField sa ID {id} nije pronađen.");

            Context.PropertyFields.Remove(field);
            await Context.SaveChangesAsync();

            return Ok($"PropertyField sa ID {id} je uspešno obrisan.");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}