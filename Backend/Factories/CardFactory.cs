using Backend.Domain;

namespace Backend.Factories
{
    public class CardFactory
    {
        public static Card CreateCard(int gameCardID,String cardType)
        {
            return cardType switch
            {
                nameof(RewardCard) => new RewardCard
                {
                    GameCardID = gameCardID
                },
                nameof(SurpriseCard) => new SurpriseCard
                {
                    GameCardID = gameCardID
                },
                _ => throw new InvalidOperationException("Unknown card type")
            };
        }

        private static Card CreateCardInternal(int gameCardId)
        {
            // ovde mapiraš ID → konkretna karta
            return gameCardId switch
            {
                1 => new RewardCard { GameCardID = 1, Reward = 200 },
                2 => new SurpriseCard { GameCardID = 2, /* ... */ },
                _ => throw new ArgumentException("Unknown card ID")
            };
        }
    }
}