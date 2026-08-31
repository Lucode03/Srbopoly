package com.example.srbopoly.factories

import com.example.srbopoly.data.gamedto.CardDeckType


data class CardTextInfo(val name: String, val description: String)

object CardCatalog {
    private val chanceCards = mapOf(
        1 to CardTextInfo("Idi na Start", "Pomeri se na polje Start i pokupi bonus za prolazak."),
        2 to CardTextInfo("Poslovni put", "Idi do Pirota."),
        3 to CardTextInfo("Poslovni put", "Idi do Kragujevca."),
        4 to CardTextInfo("Račun za struju/vodu", "Idi do najbližeg mesta za plaćanje (Vodovod ili Elektrodistribucija)."),
        5 to CardTextInfo("Izlet u prirodu", "Idi do najbližeg Nacionalnog parka."),
        6 to CardTextInfo("Dividenda", "Banka ti isplaćuje dividendu od 50."),
        7 to CardTextInfo("Slobodan prolaz", "Kartica za izlazak iz zatvora. Zadrži je dok ti ne zatreba."),
        8 to CardTextInfo("Nazad na sigurno", "Vrati se 3 polja unazad."),
        9 to CardTextInfo("Uhapšen si", "Idi pravo u zatvor."),
        10 to CardTextInfo("Račun za popravke", "Opšte popravke: plati 25 po kući i 100 po hotelu."),
        11 to CardTextInfo("Kazna za brzu vožnju", "Plati kaznu od 15."),
        12 to CardTextInfo("Poslovni put", "Idi do Novog Sada."),
        13 to CardTextInfo("Poslovni put", "Idi do Beograda."),
        14 to CardTextInfo("Izabran za predsednika odbora", "Plati svakom igraču po 50."),
        15 to CardTextInfo("Stambena obveznica", "Sazrela je tvoja stambena obveznica, pokupi 150."),
        16 to CardTextInfo("Nagrada na kvizu", "Osvojio si nagradu na kvizu, pokupi 100."),
        17 to CardTextInfo("Novi početak", "Pomeri se na polje Start i preuzmi bonus."),
        18 to CardTextInfo("Prekoračenje brzine", "Odmah idi u zatvor."),
        19 to CardTextInfo("Napred punom brzinom", "Pomeri se dva polja unapred."),
        20 to CardTextInfo("Neočekivana prepreka", "Pomeri se dva polja unazad."),
        21 to CardTextInfo("Mali napredak", "Pomeri se jedno polje unapred."),
        22 to CardTextInfo("Kratak korak", "Pomeri se jedno polje unazad."),
        23 to CardTextInfo("Dobitak na lutriji!", "Osvojio si nagradu na lutriji."),
        24 to CardTextInfo("Srećan dan!", "Izgleda da je neko izgubio novac. Šteta, sada su vaše!"),
        25 to CardTextInfo("Povraćaj poreza", "Dobijate povraćaj poreza."),
        26 to CardTextInfo("Prodaja imovine", "Uspešno ste prodali imovinu.")
    )

    private val surpriseCards = mapOf(
        1 to CardTextInfo("Idi na Start", "Pomeri se na polje Start i pokupi bonus za prolazak."),
        2 to CardTextInfo("Greška banke", "Greška banke u tvoju korist, pokupi 200."),
        3 to CardTextInfo("Doktorski računi", "Plati doktorske račune od 50."),
        4 to CardTextInfo("Prodaja akcija", "Od prodaje akcija dobijaš 50."),
        5 to CardTextInfo("Slobodan prolaz", "Kartica za izlazak iz zatvora. Zadrži je dok ti ne zatreba."),
        6 to CardTextInfo("Uhapšen si", "Idi pravo u zatvor."),
        7 to CardTextInfo("Veče opere", "Plati 50 svakom igraču za veče opere."),
        8 to CardTextInfo("Fond za odmor", "Sazreo je tvoj fond za odmor, primi 100."),
        9 to CardTextInfo("Povraćaj poreza", "Pokupi 20 na ime povraćaja poreza."),
        10 to CardTextInfo("Rođendan", "Tvoj je rođendan, pokupi 10 od svakog igrača."),
        11 to CardTextInfo("Životno osiguranje", "Sazrelo je tvoje životno osiguranje, pokupi 100."),
        12 to CardTextInfo("Bolnički troškovi", "Plati bolničke troškove od 100."),
        13 to CardTextInfo("Školarina", "Plati školarinu od 50."),
        14 to CardTextInfo("Konsultantski honorar", "Primi konsultantski honorar od 25."),
        15 to CardTextInfo("Popravka ulice", "Popravka ulice: plati 40 po kući i 115 po hotelu."),
        16 to CardTextInfo("Takmičenje lepote", "Osvojio si drugu nagradu na takmičenju lepote, pokupi 10."),
        17 to CardTextInfo("Nasledstvo", "Nasledio si 100."),
        18 to CardTextInfo("Popravka", "Plati troškove popravki."),
        19 to CardTextInfo("Pozajmica", "Morate pozajmiti prijatelju novac."),
        20 to CardTextInfo("Rođendanski poklon", "Dobili ste novac od Vaše bake za rođendan."),
        21 to CardTextInfo("Bonus od banke", "Banka ti isplaćuje bonus."),
        22 to CardTextInfo("Nagrada za ulaganje", "Dobijaš nagradu za pametno ulaganje.")
    )

    fun infoFor(deckType: CardDeckType, cardId: Int): CardTextInfo =
        when (deckType) {
            CardDeckType.CHANCE -> chanceCards[cardId]
            CardDeckType.SURPRISE -> surpriseCards[cardId]
        } ?: CardTextInfo("Nepoznata kartica", "")
}