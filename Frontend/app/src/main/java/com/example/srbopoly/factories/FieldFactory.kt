package com.example.srbopoly.factories

import com.example.srbopoly.data.fields.BonusField
import com.example.srbopoly.data.fields.Field
import com.example.srbopoly.data.fields.FieldType
import com.example.srbopoly.data.fields.JailField
import com.example.srbopoly.data.fields.MovementField
import com.example.srbopoly.data.fields.NationalParkField
import com.example.srbopoly.data.fields.PaymentField
import com.example.srbopoly.data.fields.PropertyField
import com.example.srbopoly.data.fields.RewardCardField
import com.example.srbopoly.data.fields.SurpriseCardField

class FieldFactory
{
    companion object{
        fun createField(fieldID: Int): Field {
            var field = when (fieldID) {
                0 -> BonusField(
                    Name = "Start",
                    Bonus = 5000,
                    FieldType=FieldType.START
                )

                1 -> PropertyField(
                    Name = "Subotica",
                    Price = 250,
                    BaseRent = 40,
                    FieldType=FieldType.VOJVODINA
                )

                2 -> PropertyField(
                    Name = "Pančevo",
                    Price = 280,
                    BaseRent = 45,
                    FieldType=FieldType.VOJVODINA
                )

                3 -> PaymentField(
                    Name = "Elektrodistribucija",
                    Price = 800,
                    FieldType=FieldType.ELECTRIC_TAX
                )

                4 -> PropertyField(
                    Name = "Zrenjanin",
                    Price = 350,
                    BaseRent = 55,
                    FieldType=FieldType.VOJVODINA
                )

                5 -> SurpriseCardField(
                    Name = "Iznenađenje sa istoka",
                    FieldType=FieldType.CHANCE
                )

                6 -> PropertyField(
                    Name = "Negotin",
                    Price = 400,
                    BaseRent = 60,
                    FieldType=FieldType.ISTOCNA_SRBIJA
                )

                7 -> NationalParkField(
                    Name = "Đerdap",
                    FieldType=FieldType.NATIONAL_PARK
                )

                8 -> PropertyField(
                    Name = "Bor",
                    Price = 500,
                    BaseRent = 70,
                    FieldType=FieldType.ISTOCNA_SRBIJA
                )

                9 -> PropertyField(
                    Name = "Zaječar",
                    Price = 600,
                    BaseRent = 80,
                    FieldType=FieldType.ISTOCNA_SRBIJA
                )

                10 -> MovementField(
                    Name = "Idi u zatvor",
                    FieldType=FieldType.GO_TO_JAIL
                )

                11 -> PropertyField(
                    Name = "Vranje",
                    Price = 800,
                    BaseRent = 100,
                    FieldType=FieldType.JUZNA_SRBIJA
                )

                12 -> PropertyField(
                    Name = "Leskovac",
                    Price = 850,
                    BaseRent = 110,
                    FieldType=FieldType.JUZNA_SRBIJA
                )

                13 -> PaymentField(
                    Name = "Porez",
                    Price = 1000,
                    FieldType=FieldType.TAX
                )

                14 -> PropertyField(
                    Name = "Pirot",
                    Price = 1000,
                    BaseRent = 140,
                    FieldType=FieldType.JUZNA_SRBIJA
                )

                15 -> SurpriseCardField(
                    Name = "Iznenađenje sa juga",
                    FieldType=FieldType.CHANCE
                )

                16 -> PropertyField(
                    Name = "Kosovska Mitrovica",
                    Price = 1100,
                    BaseRent = 145,
                    FieldType=FieldType.KIM
                )

                17 -> NationalParkField(
                    Name = "Šar planina",
                    FieldType=FieldType.NATIONAL_PARK
                )

                18 -> PropertyField(
                    Name = "Prizren",
                    Price = 1300,
                    BaseRent = 160,
                    FieldType=FieldType.KIM
                )

                19 -> PropertyField(
                    Name = "Peć",
                    Price = 1400,
                    BaseRent = 180,
                    FieldType=FieldType.KIM
                )

                20 -> JailField(
                    Name = "Zatvor",
                    FieldType=FieldType.JAIL
                )

                21 -> PropertyField(
                    Name = "Smederevo",
                    Price = 1500,
                    BaseRent = 190,
                    FieldType=FieldType.SUMADIJA
                )

                22 -> PropertyField(
                    Name = "Čačak",
                    Price = 1650,
                    BaseRent = 210,
                    FieldType=FieldType.SUMADIJA
                )

                23 -> NationalParkField(
                    Name = "Kopaonik",
                    FieldType=FieldType.NATIONAL_PARK
                )

                24 -> PropertyField(
                    Name = "Kragujevac",
                    Price = 1800,
                    BaseRent = 230,
                    FieldType=FieldType.SUMADIJA
                )

                25 -> SurpriseCardField(
                    Name = "Iznenađenje sa zapada",
                    FieldType=FieldType.CHANCE
                )

                26 -> PropertyField(
                    Name = "Loznica",
                    Price = 2000,
                    BaseRent = 250,
                    FieldType=FieldType.ZAPADNA_SRBIJA
                )

                27 -> NationalParkField(
                    Name = "Tara",
                    FieldType=FieldType.NATIONAL_PARK
                )

                28 -> PropertyField(
                    Name = "Šabac",
                    Price = 2200,
                    BaseRent = 275,
                    FieldType=FieldType.ZAPADNA_SRBIJA
                )

                29 -> PropertyField(
                    Name = "Valjevo",
                    Price = 2500,
                    BaseRent = 300,
                    FieldType=FieldType.ZAPADNA_SRBIJA
                )

                30 -> BonusField(
                    Name = "Besplatan parking",
                    Bonus = 2000,
                    FieldType=FieldType.PARKING
                )

                31 -> PaymentField(
                    Name = "Vodovod",
                    Price = 2000,
                    FieldType=FieldType.WATER_TAX
                )

                32 -> PaymentField(
                    Name = "Porez",
                    Price = 1500,
                    FieldType=FieldType.TAX
                )

                33 -> SurpriseCardField(
                    Name = "Iznenađenje sa severa",
                    FieldType=FieldType.CHANCE
                )

                34 -> PropertyField(
                    Name = "Priština",
                    Price = 4000,
                    BaseRent = 500,
                    FieldType=FieldType.PREMIUM1
                )

                35 -> RewardCardField(
                    Name = "Nagrada",
                    FieldType=FieldType.REWARD
                )

                36 -> PropertyField(
                    Name = "Niš",
                    Price = 4500,
                    BaseRent = 550,
                    FieldType=FieldType.PREMIUM1
                )

                37 -> PropertyField(
                    Name = "Novi Sad",
                    Price = 5500,
                    BaseRent = 600,
                    FieldType=FieldType.PREMIUM2
                )

                38 -> NationalParkField(
                    Name = "Fruška gora",
                    FieldType=FieldType.NATIONAL_PARK
                )

                39 -> PropertyField(
                    Name = "Beograd",
                    Price = 6500,
                    BaseRent = 700,
                    FieldType=FieldType.PREMIUM2
                )

                else -> throw IllegalArgumentException("Invalid field ID: $fieldID")
            }.apply { GameFieldID = fieldID }

            return field
        }
    }
}