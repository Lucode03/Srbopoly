using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class PropertyFieldMapper
    {
        public static PropertyFieldDto ToDTO(PropertyField field)
        {
            return new PropertyFieldDto
            {
                GameFieldID = field.GameFieldID,
                Houses = field.Houses,
                Hotels = field.Hotels,
                OwnerId = field.Owner?.ID
            };
        }
        public static PropertyField ToBusiness(PropertyFieldDto dto, List<Player> players)
        {
            var owner = dto.OwnerId != null
                ? players.FirstOrDefault(p => p.ID == dto.OwnerId)
                : null;

            PropertyField propertyField= (PropertyField)FieldFactory.CreateField(dto.GameFieldID);
            propertyField.Owner = owner;
            if (owner != null)
                owner.Properties.Add(propertyField);
            return propertyField;
        }
    }
}
