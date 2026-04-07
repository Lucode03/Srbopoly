package com.example.srbopoly.data

import androidx.compose.ui.graphics.Color
import com.example.srbopoly.R
import com.example.srbopoly.enums.FieldType

val propertyFieldTypes = listOf(
    FieldType.VOJVODINA,
    FieldType.ISTOCNA_SRBIJA,
    FieldType.JUZNA_SRBIJA,
    FieldType.KIM,
    FieldType.SUMADIJA,
    FieldType.ZAPADNA_SRBIJA,
    FieldType.PREMIUM1,
    FieldType.PREMIUM2
)

data class Player(
    var id:Int,
    var Username:String,
    var Balance :Int,
    var Position:Int=0,
    var Color :String,
    var IsInJail:Boolean=false,
    var Properties: MutableMap<FieldType, Int> = propertyFieldTypes.associateWith { 0 }.toMutableMap()
){
    fun Receive(amount:Int)
    {
        Balance += amount
    }

    fun Pay(amount:Int)
    {
        Balance -= amount
    }

    fun Move(amount:Int)
    {
        val newPosition=Position+amount
        if(newPosition>40)
            Receive(5000)
        Position = newPosition % 40
    }

    fun SetPosition(amount:Int)
    {
        Position = amount
    }

    fun GoToJail()
    {
        Position = 20
    }

    fun CheckMonopoly(fieldType:FieldType):Boolean
    {
        if(fieldType !in propertyFieldTypes)
            return false

        var retVal=false
        retVal = when(fieldType) {
            FieldType.PREMIUM1 -> Properties[fieldType]==2
            FieldType.PREMIUM2 -> Properties[fieldType]==2
            else -> Properties[fieldType]==3
        }
        return retVal
    }
}

fun getFigure(color: String): Int {
    return when (color) {
        "Crvena" -> R.drawable.figure_red
        "Plava" -> R.drawable.figure_blue
        "Zelena" -> R.drawable.figure_green
        "Žuta" -> R.drawable.figure_yellow
        "Narandžasta" -> R.drawable.figure_orange
        "Bela" -> R.drawable.figure_white
        else -> R.drawable.figure_white
    }
}

fun getColor(color: String): Color {
    return when (color) {
        "Crvena" -> Color(0xFFD30000)
        "Plava" -> Color(0xFF002ECC)
        "Zelena" -> Color(0xFF00D301)
        "Žuta" -> Color(0xFFE3B000)
        "Narandžasta" -> Color(0xFFFF8400)
        "Bela" -> Color(0xFFB4B4B4)
        else -> Color.White
    }
}