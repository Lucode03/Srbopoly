using Backend.Domain;
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
                    .Select(PropertyFieldMapper.ToEntity)
                    .ToList()
            };
        }
        public static Board ToBusiness(BoardEntity entity, List<Player> players)
        {
            var board = new Board
            {
                Size = 40, // default
                Fields = new List<Field>()
            };

            //treba izmena u zavisnosti od factory
            board.Fields.AddRange(FieldFactory.CreateFields());

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
