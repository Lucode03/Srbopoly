using Backend.Persistence.Entities;
using Backend.Domain;
using Backend.Persistence.DTO;
using Backend.Repositories;
using Backend.Persistence.Mappers;
using Microsoft.EntityFrameworkCore.Storage;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class PropertyFieldController : ControllerBase
{
    private readonly PropertyFieldRepository _repository;

    public PropertyFieldController(PropertyFieldRepository repository)
    {
        _repository = repository;
    }

    [HttpPost("CreatePropertyField")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<PropertyFieldDto>> CreatePropertyField(int boardId, [FromBody] PropertyFieldDto dto)
    {
        try
        {
            var field = await _repository.CreateAsync(boardId, PropertyFieldMapper.ToBusiness(dto, new List<Player>()));
            return Ok(PropertyFieldMapper.ToDTO(field));
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

    [HttpGet("GetById/{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult<PropertyFieldDto>> GetById(int id)
    {
        try
            {
                var field = await _repository.GetByIdAsync(id);
                if (field == null) return NotFound($"PropertyField sa ID {id} nije pronađen.");

                return Ok(PropertyFieldMapper.ToDTO(field));
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
                var fields = await _repository.GetAllForBoardAsync(boardId);
                if (!fields.Any())
                    return NotFound($"Nema property fieldova za Board ID {boardId}.");

                return Ok(fields.Select(PropertyFieldMapper.ToDTO).ToList());
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
            var updated = await _repository.UpdateAsync(id, PropertyFieldMapper.ToBusiness(dto, new List<Player>()));
            return Ok(PropertyFieldMapper.ToDTO(updated));
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
            return Ok($"PropertyField sa ID {id} je uspešno obrisan.");
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