using Backend.Domain;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class UserMapper
    {
       public static UserDto ToDto(UserEntity entity)
        {
            return new UserDto
            {
                Id = entity.ID,
                Username = entity.Username,
                Points = entity.Points
            };
        }

        public static UserEntity ToEntity(UserDto dto)
        {
            return new UserEntity
            {
                ID = dto.Id,
                Username = dto.Username,
                Points = dto.Points
            };
        }
    }
}
