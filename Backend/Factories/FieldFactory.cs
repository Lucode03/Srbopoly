using Backend.Domain;

namespace Backend.Factories
{
    public class FieldFactory
    {
        public static Field CreateField(int FieldID)
        {
            Field field = FieldID switch
            {
                0 => new BonusField
                {
                    Name = "Start",
                    Bonus = 5000,
                },
                1 => new PropertyField
                {
                    Name = "Subotica",
                    Price = 250,
                    BaseRent = 40,
                    Type = "Vojvodina"
                },
                2 => new PropertyField
                {
                    Name = "Pancevo",
                    Price = 280,
                    BaseRent = 45,
                    Type = "Vojvodina"
                },
                3 => new PaymentField
                {
                    Name = "Elektrodistribucija",
                    Price = 800
                },
                4 => new PropertyField
                {
                    Name = "Zrenjanin",
                    Price = 350,
                    BaseRent = 55,
                    Type = "Vojvodina"
                },
                5 => new SurpriseCardField
                {
                    Name = "Iznenadjenje 1"
                },
                6 => new PropertyField
                {
                    Name = "Negotin",
                    Price = 400,
                    BaseRent = 60,
                    Type = "Istocna Srbija"
                },
                7 => new NationalParkField
                {
                    Name = "Djerdap"
                },
                8 => new PropertyField
                {
                    Name = "Bor",
                    Price = 500,
                    BaseRent = 70,
                    Type = "Istocna Srbija"
                },
                9 => new PropertyField
                {
                    Name = "Zajecar",
                    Price = 600,
                    BaseRent = 80,
                    Type = "Istocna Srbija"
                },
                10 => new MovementField
                {
                    Name = "Idi u zatvor",
                },
                11 => new PropertyField
                {
                    Name = "Vranje",
                    Price = 800,
                    BaseRent = 100,
                    Type = "Juzna Srbija"
                },
                12 => new PropertyField
                {
                    Name = "Leskovac",
                    Price = 850,
                    BaseRent = 110,
                    Type = "Juzna Srbija"
                },
                13 => new PaymentField
                {
                    Name = "Porez",
                    Price = 1000
                },
                14 => new PropertyField
                {
                    Name = "Pirot",
                    Price = 1000,
                    BaseRent = 140,
                    Type = "Juzna Srbija"
                },
                15 => new SurpriseCardField
                {
                    Name = "Iznenadjenje 2"
                },
                16 => new PropertyField
                {
                    Name = "Kosovska Mitrovica",
                    Price = 1100,
                    BaseRent = 145,
                    Type = "Kosovo i Metohija"
                },
                17 => new NationalParkField
                {
                    Name = "Sar planina"
                },
                18 => new PropertyField
                {
                    Name = "Prizren",
                    Price = 1300,
                    BaseRent = 160,
                    Type = "Kosovo i Metohija"
                },
                19 => new PropertyField
                {
                    Name = "Pec",
                    Price = 1400,
                    BaseRent = 180,
                    Type = "Kosovo i Metohija"
                },
                20 => new JailField
                {
                    Name = "Zatvor",
                },
                1 => new PropertyField
                {
                    Name = "Subotica",
                    Price = 250,
                    BaseRent = 40,
                    Type = "Vojvodina"
                },
                2 => new PropertyField
                {
                    Name = "Pancevo",
                    Price = 280,
                    BaseRent = 45,
                    Type = "Vojvodina"
                },
                3 => new PaymentField
                {
                    Name = "Elektrodistribucija",
                    Price = 800
                },
                4 => new PropertyField
                {
                    Name = "Zrenjanin",
                    Price = 350,
                    BaseRent = 55,
                    Type = "Vojvodina"
                },
                5 => new SurpriseCardField
                {
                    Name = "Iznenadjenje 1"
                },
                6 => new PropertyField
                {
                    Name = "Negotin",
                    Price = 400,
                    BaseRent = 60,
                    Type = "Istocna Srbija"
                },
                7 => new NationalParkField
                {
                    Name = "Djerdap"
                },
                8 => new PropertyField
                {
                    Name = "Bor",
                    Price = 500,
                    BaseRent = 70,
                    Type = "Istocna Srbija"
                },
                9 => new PropertyField
                {
                    Name = "Zajecar",
                    Price = 600,
                    BaseRent = 80,
                    Type = "Istocna Srbija"
                },
                30 => new BonusField
                {
                    Name = "Besplatan parking",
                    Bonus = 2000,
                },
                1 => new PropertyField
                {
                    Name = "Subotica",
                    Price = 250,
                    BaseRent = 40,
                    Type = "Vojvodina"
                },
                2 => new PropertyField
                {
                    Name = "Pancevo",
                    Price = 280,
                    BaseRent = 45,
                    Type = "Vojvodina"
                },
                3 => new PaymentField
                {
                    Name = "Elektrodistribucija",
                    Price = 800
                },
                4 => new PropertyField
                {
                    Name = "Zrenjanin",
                    Price = 350,
                    BaseRent = 55,
                    Type = "Vojvodina"
                },
                5 => new SurpriseCardField
                {
                    Name = "Iznenadjenje 1"
                },
                6 => new PropertyField
                {
                    Name = "Negotin",
                    Price = 400,
                    BaseRent = 60,
                    Type = "Istocna Srbija"
                },
                7 => new NationalParkField
                {
                    Name = "Djerdap"
                },
                8 => new PropertyField
                {
                    Name = "Bor",
                    Price = 500,
                    BaseRent = 70,
                    Type = "Istocna Srbija"
                },
                9 => new PropertyField
                {
                    Name = "Zajecar",
                    Price = 600,
                    BaseRent = 80,
                    Type = "Istocna Srbija"
                },                
                _ => throw new NotImplementedException()
            };
            field.GameFieldID = FieldID;

            return field;
        }
    }
}