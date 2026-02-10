using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class PropertyFieldMapper
    {
        public static PropertyFieldEntity ToEntity(PropertyField field)
        {
            return new PropertyFieldEntity
            {
                GameFieldID = field.GameFieldID,
                Houses = field.Houses,
                Hotels = field.Hotels,
                OwnerID = field.Owner?.ID,
                Owner = field.Owner!=null? PlayerMapper.ToEntity(field.Owner)
                        : null
            };
        }
        public static PropertyField ToBusiness(PropertyFieldEntity entity, List<Player> players)
        {
            var owner = entity.OwnerID != null
                ? players.FirstOrDefault(p => p.ID == entity.OwnerID)
                : null;

            PropertyField propertyField= (PropertyField)FieldFactory.CreateField(entity.GameFieldID);
            propertyField.Owner = owner;
            if (owner != null)
                owner.Properties.Add(propertyField);
            return propertyField;
        }
    }
}
