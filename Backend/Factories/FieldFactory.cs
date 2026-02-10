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
                    Name = "Pančevo",
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
                    Name = "Iznenađenje sa istoka"
                },
                6 => new PropertyField
                {
                    Name = "Negotin",
                    Price = 400,
                    BaseRent = 60,
                    Type = "Istočna Srbija"
                },
                7 => new NationalParkField
                {
                    Name = "Đerdap"
                },
                8 => new PropertyField
                {
                    Name = "Bor",
                    Price = 500,
                    BaseRent = 70,
                    Type = "Istočna Srbija"
                },
                9 => new PropertyField
                {
                    Name = "Zaječar",
                    Price = 600,
                    BaseRent = 80,
                    Type = "Istočna Srbija"
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
                    Type = "Južna Srbija"
                },
                12 => new PropertyField
                {
                    Name = "Leskovac",
                    Price = 850,
                    BaseRent = 110,
                    Type = "Južna Srbija"
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
                    Type = "Južna Srbija"
                },
                15 => new SurpriseCardField
                {
                    Name = "Iznenađenje sa juga"
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
                    Name = "Šar planina"
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
                    Name = "Peć",
                    Price = 1400,
                    BaseRent = 180,
                    Type = "Kosovo i Metohija"
                },
                20 => new JailField
                {
                    Name = "Zatvor",
                },
                21 => new PropertyField
                {
                    Name = "Smederevo",
                    Price = 1500,
                    BaseRent = 190,
                    Type = "Šumadija"
                },
                22 => new PropertyField
                {
                    Name = "Čačak",
                    Price = 1650,
                    BaseRent = 210,
                    Type = "Šumadija"
                },
                23 => new NationalParkField
                {
                    Name = "Kopaonik"
                },
                24 => new PropertyField
                {
                    Name = "Kragujevac",
                    Price = 1800,
                    BaseRent = 230,
                    Type = "Šumadija"
                },
                25 => new SurpriseCardField
                {
                    Name = "Iznenađenje sa zapada"
                },
                26 => new PropertyField
                {
                    Name = "Loznica",
                    Price = 2000,
                    BaseRent = 250,
                    Type = "Zapadna Srbija"
                },
                27 => new NationalParkField
                {
                    Name = "Tara"
                },
                28 => new PropertyField
                {
                    Name = "Šabac",
                    Price = 2200,
                    BaseRent = 275,
                    Type = "Zapadna Srbija"
                },
                29 => new PropertyField
                {
                    Name = "Valjevo",
                    Price = 2500,
                    BaseRent = 300,
                    Type = "Zapadna Srbija"
                },
                30 => new BonusField
                {
                    Name = "Besplatan parking",
                    Bonus = 2000,
                },
                31 => new PaymentField
                {
                    Name = "Vodovod",
                    Price = 2000,
                },
                32 => new PaymentField
                {
                    Name = "Porez",
                    Price = 1500,
                },
                33 => new SurpriseCardField
                {
                    Name = "Iznenađenje sa severa",
                },
                34 => new PropertyField
                {
                    Name = "Priština",
                    Price = 4000,
                    BaseRent = 500,
                    Type = "Premijum lokacija 1"
                },
                35 => new RewardCardField
                {
                    Name = "Nagrada"
                },
                36 => new PropertyField
                {
                    Name = "Niš",
                    Price = 4500,
                    BaseRent = 550,
                    Type = "Premijum lokacija 1"
                },
                37 => new PropertyField
                {
                    Name = "Novi Sad",
                    Price = 5500,
                    BaseRent = 600,
                    Type = "Premijum lokacija 2"
                },
                38 => new NationalParkField
                {
                    Name = "Fruška gora"
                },
                39 => new PropertyField
                {
                    Name = "Beograd",
                    Price = 6500,
                    BaseRent = 700,
                    Type = "Premijum lokacija 2"
                },                
                _ => throw new NotImplementedException()
            };
            field.GameFieldID = FieldID;

            return field;
        }
    }
}