using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class BoardMapper
    {
        public static BoardEntity ToEntity(Board board)
        {
            return new BoardEntity
            {
                PropertyFields = board.Fields
                    .OfType<PropertyField>()         
                    .Select(field => PropertyFieldMapper.ToEntity(field))
                    .ToList()
            };
        }
        public static Board ToBusiness(BoardEntity entity, List<Player> players)
        {
            var board = new Board
            {
                Size = 40, // default
                Fields = new List<Field>(40)
            };

            foreach (PropertyFieldEntity field in entity.PropertyFields)
            {
                board.Fields[field.GameFieldID]=FieldFactory.CreateField(field.GameFieldID);
            }

            if (entity.PropertyFields != null)
            {
                foreach (var propEntity in entity.PropertyFields)
                {
                    var propertyField = PropertyFieldMapper.ToBusiness(propEntity, players);

                    board.Fields[propertyField.GameFieldID] = propertyField;
                }
            }

            return board;
        }
    }
}
