using Backend.Domain;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class UserMapper
    {
        public static UserDto ToEntity(User user)
        {
            return new UserDto
            {
                Id = user.ID,
                Username = user.Username,
                Points = user.Points
            };
        }

        public static User ToBusiness(UserDto dto)
        {
            return new User
            {
                ID = dto.Id,
                Username = dto.Username,
                Points = dto.Points
            };
        }
    }
}
