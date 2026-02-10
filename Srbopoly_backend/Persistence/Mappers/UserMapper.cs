using Backend.Domain;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class UserMapper
    {
        public static UserEntity ToEntity(User user)
        {
            return new UserEntity
            {
                ID = user.ID,
                Username = user.Username,
                Points = user.Points
            };
        }

        public static User ToBusiness(UserEntity entity)
        {
            return new User
            {
                ID = entity.ID,
                Username = entity.Username,
                Points = entity.Points
            };
        }
    }
}
