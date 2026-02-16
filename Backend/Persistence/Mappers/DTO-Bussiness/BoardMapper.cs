using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class BoardMapper
    {
        public static BoardDto ToDTO(Board board)
        {
            return new BoardDto
            {
                PropertyFields = board.Fields?
                    .OfType<PropertyField>()         
                    .Select(field => PropertyFieldMapper.ToDTO(field))
                    .ToList() ?? new List<PropertyFieldDto>()
            };
        }
        public static Board ToBusiness(BoardDto dto, List<Player> players)
        {
            int boardSize = 40; // default
            var board = new Board
            {
                Size = boardSize,
                Fields = new List<Field>(new Field[boardSize])
            };

            if (dto.PropertyFields != null)
            {
                foreach (PropertyFieldDto propertyDto in dto.PropertyFields)
                {
                    PropertyField propertyField = PropertyFieldMapper.ToBusiness(propertyDto, players);
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
