using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class CardMapper
    {
        public static CardDto ToDto(Card card)
        {
            return new CardDto
            {
                //CardType = card.GetType().Name,
                GameCardID = card.GameCardID
            };

        }
        public static Card ToBusiness(CardDto dto)
        {
            return CardFactory.CreateCard(dto.GameCardID);
        }
    }
}
