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
            int boardSize = 40; // default
            var board = new Board
            {
                Size = boardSize,
                Fields = new List<Field>(new Field[boardSize])
            };

            if (entity.PropertyFields != null)
            {
                foreach (PropertyFieldEntity propertyEntity in entity.PropertyFields)
                {
                    PropertyField propertyField = PropertyFieldMapper.ToBusiness(propertyEntity, players);
                    board.Fields[propertyField.GameFieldID] = propertyField;
                }
            }

            for (int i = 0; i < boardSize; i++)
            {
                if (board.Fields[i] == null)
                {
                    board.Fields[i] = FieldFactory.CreateField(i);
                }
            }

            return board;
        }
    }
}
