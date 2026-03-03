using Backend.Persistence.Entities;
using Backend.Repositories;
using Backend.Persistence.Mappers;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class BoardController : ControllerBase
{
    private readonly BoardRepository _repository;

    public BoardController(BoardRepository repository)
    {
        _repository = repository;
    }

    [HttpPost("CreateBoard")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<BoardDto>> CreateBoard(int gameId)
    {
        try
        {
            var board = await _repository.CreateBoardAsync(gameId);
            var dto = BoardMapper.ToDTO(board);
            
            return Ok(dto);
        }
        catch (KeyNotFoundException e)
        {
            return NotFound(e.Message);
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
            var board = await _repository.GetBoardByGameAsync(gameId);

            if (board == null)
                return NotFound($"Board za Game ID {gameId} nije pronađen.");

            var dto = BoardMapper.ToDTO(board);
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
            var field = PropertyFieldMapper.ToBusiness(fieldDto, new List<Domain.Player>());
            var addedField = await _repository.AddPropertyFieldAsync(boardId, field);
            var dto = PropertyFieldMapper.ToDTO(addedField);

            return Ok(dto);
        }
        catch (KeyNotFoundException e)
        {
            return NotFound(e.Message);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}