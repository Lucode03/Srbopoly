package com.example.srbopoly.factories

import com.example.srbopoly.data.gamedto.CardDeckType

object CardCatalog {
    private val chanceTexts = mapOf(
        1 to "Idi na Start",
        2 to "Idi do Pirota",
        3 to "Idi do Kragujevca",
        4 to "Idi do najbližeg mesta za plaćanje (Vodovod/Elektrodistribucija)",
        5 to "Idi do najbližeg Nacionalnog parka",
        6 to "Banka ti isplaćuje dividendu od 50",
        7 to "Kartica za izlazak iz zatvora",
        8 to "Vrati se 3 polja unazad",
        9 to "Idi u zatvor",
        10 to "Opšte popravke: 25 po kući, 100 po hotelu",
        11 to "Kazna za brzu vožnju, plati 15",
        12 to "Idi do Novog Sada",
        13 to "Idi do Beograda",
        14 to "Izabran si za predsednika odbora, plati svakom igraču 50",
        15 to "Sazrela je tvoja stambena obveznica, pokupi 150",
        16 to "Osvojio si nagradu na kvizu, pokupi 100",
        17 to "Novi početak",
        18 to "Prekoračenje brzine",
        19 to "Napred punom brzinom",
        20 to "Neočekivana prepreka",
        21 to "Mali napredak",
        22 to "Kratak korak",
        23 to "Dobitak na lutriji!",
        24 to "Srećan dan!",
        25 to "Povraćaj poreza",
        26 to "Prodaja imovine"
    )

    private val surpriseTexts = mapOf(
        1 to "Idi na Start",
        2 to "Greška banke u tvoju korist, pokupi 200",
        3 to "Doktorski računi, plati 50",
        4 to "Od prodaje akcija dobijaš 50",
        5 to "Kartica za izlazak iz zatvora",
        6 to "Idi u zatvor",
        7 to "Veče opere, pokupi 50 od svakog igrača",
        8 to "Sazreo je tvoj fond za odmor, primi 100",
        9 to "Povraćaj poreza, pokupi 20",
        10 to "Tvoj je rođendan, pokupi 10 od svakog igrača",
        11 to "Sazrelo je tvoje životno osiguranje, pokupi 100",
        12 to "Plati bolničke troškove, 100",
        13 to "Plati školarinu, 50",
        14 to "Primi konsultantski honorar, 25",
        15 to "Popravka ulice: 40 po kući, 115 po hotelu",
        16 to "Osvojio si drugu nagradu na takmičenju lepote, pokupi 10",
        17 to "Nasledio si 100",
        18 to "Popravka",
        19 to "Pozajmica",
        20 to "Rođendanski poklon",
        21 to "Bonus od banke",
        22 to "Nagrada za ulaganje"
    )

    fun textFor(deckType: CardDeckType, cardId: Int): String =
        when (deckType) {
            CardDeckType.CHANCE -> chanceTexts[cardId]
            CardDeckType.SURPRISE -> surpriseTexts[cardId]
        } ?: "Nepoznata kartica"
}