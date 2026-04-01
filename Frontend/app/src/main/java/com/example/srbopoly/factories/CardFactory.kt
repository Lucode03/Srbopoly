package com.example.srbopoly.factories

import com.example.srbopoly.data.Card
import com.example.srbopoly.data.RewardCard
import com.example.srbopoly.data.SurpriseCard

class CardFactory
{
    companion object{
        fun createCard(cardID: Int): Card {
            var card = when (cardID) {
                0 -> SurpriseCard(
                    CardName = "Novi početak",
                    Description = "Pomeri se na polje START i preuzmi bonus.",
                    Amount = 0,
                    Type = "Position"
                )

                1 -> SurpriseCard(
                    CardName = "Prekoračenje brzine",
                    Description = "Odmah idi u zatvor.",
                    Amount = 20,
                    Type = "Position"
                )

                2 -> SurpriseCard(
                    CardName = "Napred punom brzinom",
                    Description = "Pomeri se dva polja unapred.",
                    Amount = 2,
                    Type = "Movement"
                )

                3 -> SurpriseCard(
                    CardName = "Neočekivana prepreka",
                    Description = "Pomeri se dva polja unazad.",
                    Amount = -2,
                    Type = "Movement"
                )

                4 -> SurpriseCard(
                    CardName = "Mali napredak",
                    Description = "Pomeri se dva polja unapred.",
                    Amount = 1,
                    Type = "Movement"
                )

                5 -> SurpriseCard(
                    CardName = "Kratak korak",
                    Description = "Pomeri se jedno polja unazad.",
                    Amount = -1,
                    Type = "Movement"
                )

                6 -> SurpriseCard(
                    CardName = "Popravka",
                    Description = "Plati troškove popravki.",
                    Amount = -300,
                    Type = "Balance"
                )

                7 -> SurpriseCard(
                    CardName = "Dobitak na lutriji!",
                    Description = "Osvojio si nagradu na lutriji.",
                    Amount = 1000,
                    Type = "Balance"
                )

                8 -> SurpriseCard(
                    CardName = "Pozajmica",
                    Description = "Morate pozajmiti prijatelju novac.",
                    Amount = -550,
                    Type = "Balance"
                )

                9 -> SurpriseCard(
                    CardName = "Srećan dan!",
                    Description = "Izgleda da je neko izgubio novac. Šteta, sada su vaše!",
                    Amount = 500,
                    Type = "Balance"
                )

                10 -> RewardCard(
                    CardName = "Rođendanski poklon",
                    Description = "Dobili ste novac od Vaše bake za rođendan.",
                    Reward = 1500
                )

                11 -> RewardCard(
                    CardName = "Povraćaj poreza",
                    Description = "Dobijate povraćaj poreza.",
                    Reward = 400
                )

                12 -> RewardCard(
                    CardName = "Bonus od banke",
                    Description = "Banka ti isplaćuje bonus.",
                    Reward = 200
                )

                13 -> RewardCard(
                    CardName = "Prodaja imovine",
                    Description = "Uspešno ste prodali imovinu.",
                    Reward = 700
                )

                14 -> RewardCard(
                    CardName = "Nagrada za ulaganje",
                    Description = "Dobijaš nagradu za pametno ulaganje.",
                    Reward = 1000
                )

                else -> throw IllegalArgumentException("Invalid card ID: $cardID")
            };
            card.GameCardID = cardID;

            return card;
        }
    }
}