using Backend.Domain;
using Backend.Factories;
using Backend.Persistence.Entities;

namespace Backend.Persistence.Mappers
{
    public static class CardMapperDE
    {
        public static CardEntity ToEntity(CardDto card)
        {
            return new CardEntity
            {
                //CardType = card.GetType().Name,
                GameCardID = card.GameCardID,
                ID = card.Id
            };

        }
        public static CardDto ToDto(CardEntity cardEntity)
        {
            return new CardDto
            {
              GameCardID = cardEntity.GameCardID,
              Id = cardEntity.GameCardID
            };
        }
    }
}
