using Backend.Persistence.Entities;

namespace Backend.Persistence.Controllers;

[ApiController]
[Route("[controller]")]
public class UserController : ControllerBase 
{
    public SrbopolyContext Context { get; set; }

    public UserController(SrbopolyContext c) {
        Context = c;
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
            var exists = await Context.Users
                .AnyAsync(u => u.Username == username);
            if (exists)
                return BadRequest("Username već postoji.");
            var user = new UserEntity
            {
                Username = username,
                Points = 0
            };

            await Context.Users.AddAsync(user);
            await Context.SaveChangesAsync();
            return Ok(user);
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
            var user = await Context.Users.FindAsync(id);
            if (user == null)
                return NotFound($"Korisnik sa ID {id} nije pronađen.");
            return Ok(user);
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
            var user = await Context.Users
                .FirstOrDefaultAsync(u => u.Username == username);
            if (user == null)
                return NotFound($"Korisnik sa username '{username}' nije pronađen.");

            return Ok(user);
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
            var user = await Context.Users.FindAsync(id);

            if (user == null)
                return NotFound($"Korisnik sa ID {id} nije pronađen.");

            if (user.Points + points < 0)
                return BadRequest("Korisnik ne može imati negativan broj bodova.");

            user.Points += points;

            await Context.SaveChangesAsync();
            return Ok(user);
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
            var user = await Context.Users.FindAsync(id);
            if (user == null)
                return NotFound($"Korisnik sa ID {id} nije pronađen.");

            Context.Users.Remove(user);
            await Context.SaveChangesAsync();

            return Ok($"Korisnik sa ID {id} je uspešno obrisan.");
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
            var user = await Context.Users
                .FirstOrDefaultAsync(u => u.Username == username);

            if (user == null)
                return NotFound($"Korisnik sa username '{username}' nije pronađen.");

            Context.Users.Remove(user);
            await Context.SaveChangesAsync();

            return Ok($"Korisnik '{username}' je uspešno obrisan.");
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
            var users = await Context.Users.ToListAsync();

            if (users == null)
                return NotFound("Nema registrovanih korisnika.");

            return Ok(users);
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
            IQueryable<UserEntity> query = Context.Users;

            query = query.OrderByDescending(u => u.Points);
            if (top > 0)
                query = query.Take(top);

            var users = await query.ToListAsync();

            return Ok(users);
        }
        catch (Exception e)
        {
            return BadRequest(e.Message);
        }
    }
}

