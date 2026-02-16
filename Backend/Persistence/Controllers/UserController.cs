using Backend.Persistence.Entities;
using Backend.Persistence.Mappers;
using Backend.Repositories;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class UserController : ControllerBase 
{
    private readonly UserRepository _repository;

        public UserController(UserRepository repository)
        {
            _repository = repository;
        }

    [HttpPost("CreateUser")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult> CreateUser([FromBody] string username)
    {
        try
            {
                if (string.IsNullOrWhiteSpace(username))
                    return BadRequest("Username nije validan.");

                var user = await _repository.CreateUserAsync(username);
                return Ok(UserMapper.ToEntity(user));
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
    public async Task<ActionResult> GetById(int id)
    {
        try
            {
                var user = await _repository.GetByIdAsync(id);
                if (user == null) return NotFound($"Korisnik sa ID {id} nije pronađen.");

                return Ok(UserMapper.ToEntity(user));
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
    }

    [HttpGet("GetByUsername/{username}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult> GetByUsername(string username)
    {
        try
            {
                var user = await _repository.GetByUsernameAsync(username);
                if (user == null) return NotFound($"Korisnik sa username '{username}' nije pronađen.");

                return Ok(UserMapper.ToEntity(user));
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
    }

    [HttpPut("AddPoints/{id}/{points}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult> AddPoints(int id, int points)
    {
         try
            {
                var user = await _repository.AddPointsAsync(id, points);
                return Ok(UserMapper.ToEntity(user));
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

    [HttpDelete("DeleteById/{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult> DeleteById(int id)
    {
        try
            {
                await _repository.DeleteByIdAsync(id);
                return Ok($"Korisnik sa ID {id} je uspešno obrisan.");
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

    [HttpDelete("DeleteByUsername/{username}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult> DeleteByUsername(string username)
    {
        try
        {
            await _repository.DeleteByUsernameAsync(username);
            return Ok($"Korisnik '{username}' je uspešno obrisan.");
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

    [HttpGet("GetAllUsers")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult> GetAllUsers()
    {
        try
            {
                var users = await _repository.GetAllAsync();
                if (users.Count == 0) return NotFound("Nema registrovanih korisnika.");

                return Ok(users.Select(UserMapper.ToEntity).ToList());
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
    }

    [HttpGet("GetUsers")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public async Task<ActionResult> GetUsers([FromQuery] int top = 0)
    {
        try
            {
                var users = await _repository.GetTopUsersAsync(top);
                return Ok(users.Select(UserMapper.ToEntity).ToList());
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
    }
}

