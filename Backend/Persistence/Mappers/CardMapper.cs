using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class CardMapper
    {
        public static CardEntity ToEntity(Card card)
        {
            return new CardEntity
            {
                //CardType = card.GetType().Name,
                GameCardID = card.GameCardID
            };

        }
        public static Card ToBusiness(CardEntity entity)
        {
            return CardFactory.CreateCard(entity.GameCardID);
        }
    }
}
