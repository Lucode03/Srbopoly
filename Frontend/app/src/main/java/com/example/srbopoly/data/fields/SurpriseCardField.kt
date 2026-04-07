package com.example.srbopoly.data.fields

import com.example.srbopoly.R
import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player
import com.example.srbopoly.enums.FieldType
import kotlin.math.absoluteValue

class SurpriseCardField(
    Name: String,
    FieldType:FieldType
):Field(Name,FieldType) {

    override fun Action(player: Player,game: Game?):String
    {
//        val card=game!!.DrawSurpriseCard()
//        card.Apply(player)
//
//        var retString= "Igrač ${player.Username} je izvukao karticu ${card.CardName} "
//
//        if(card.Type=="Position")
//        {
//            player.SetPosition(card.Amount)
//            retString+="i ide na polje sa brojem ${card.Amount}"
//        }
//        else if(card.Type=="Movement")
//        {
//            player.Move(card.Amount)
//            retString+="i pomera se "+ if(card.Amount>0) "unapred" else "unazad"
//            retString+="za broj polja:${card.Amount.absoluteValue}"
//        }
//        else if(card.Type=="Balance")
//        {
//            player.Receive(card.Amount)
//            retString+="i dobio nagradu od: ${card.Amount} 💵"
//        }
//        return retString
        return ""
    }
}