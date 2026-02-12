using Backend.Persistence.Entities;
using Backend.Domain;
using Backend.Persistence.DTO;
using Backend.Repositories;
using Backend.Persistence.Mappers;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class CardController : ControllerBase
{
    private readonly CardRepository _repository;

    public CardController(CardRepository repository)
    {
        _repository = repository;
    }

    [HttpPost("CreateCard")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<CardDto>> CreateCard(int gameId, [FromBody] CardDto dto)
    {
        try
        {
            var card = await _repository.CreateCardAsync(gameId, CardMapper.ToBusiness(dto));
            return Ok(CardMapper.ToDto(card));
        }
        catch (KeyNotFoundException knf)
        {
            return NotFound(knf.Message);
        }
        catch (InvalidOperationException ioe)
        {
            return BadRequest(ioe.Message);
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
            var card = await _repository.GetByIdAsync(id);
            if (card == null) return NotFound($"Card sa ID {id} nije pronađena.");

            return Ok(CardMapper.ToDto(card));
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
            var cards = await _repository.GetByGameAsync(gameId);
            return Ok(cards.Select(CardMapper.ToDto).ToList());
        }
        catch (KeyNotFoundException knf)
        {
            return NotFound(knf.Message);
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
            await _repository.DeleteAsync(id);
            return Ok($"Card sa ID {id} je uspešno obrisana.");
        }
        catch (KeyNotFoundException knf)
        {
            return NotFound(knf.Message);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}