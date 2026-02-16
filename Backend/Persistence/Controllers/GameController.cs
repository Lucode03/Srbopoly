using Backend.Persistence.Entities;
using Backend.Persistence.DTO;
using Backend.Domain;
using Backend.Repository;
using Backend.Persistence.Mappers;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class GameController : ControllerBase
{
    private readonly IGameRepository _repository;

    public GameController(IGameRepository repository)
    {
        _repository = repository;
    }

    [HttpPost("CreateGame")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<GameDto>> CreateGame([FromBody] int maxTurns)
    {
        try
        {
            var game = new Game
            {
                Status = GameStatus.Paused,
                MaxTurns = maxTurns,
                CurrentTurn = 0,
                CurrentPlayerIndex = 0,
                GameBoard = new Board()
            };

            var createdGame = await _repository.CreateAsync(game);
            return Ok(GameMapper.ToEntity(createdGame));
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
            var game = await _repository.GetByIdAsync(id);
            if (game == null)
                return NotFound($"Game sa ID {id} nije pronađen.");

            return Ok(GameMapper.ToEntity(game));
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
            var games = await _repository.GetAllAsync();
            return Ok(games.Select(GameMapper.ToEntity).ToList());
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
            var game = await _repository.GetByIdAsync(id);
            if (game == null)
                return NotFound($"Game sa ID {id} nije pronađen.");

            await _repository.DeleteAsync(game);
            return Ok($"Game sa ID {id} je uspešno obrisan.");
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}