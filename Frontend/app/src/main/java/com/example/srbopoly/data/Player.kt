package com.example.srbopoly.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.srbopoly.R
import com.example.srbopoly.data.fields.PropertyField

data class Player(
    var id:Int,
    var Username:String,
    var Balance :Int,
    var Position:Int=0,
    var Color :String,
    var IsInJail:Boolean=false,
    var Properties:List<PropertyField> = emptyList()
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
        Position = (Position + amount) % 40
    }

    fun SetPosition(amount:Int)
    {
        Position = amount
    }

    fun GoToJail()
    {
        Position = 20
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