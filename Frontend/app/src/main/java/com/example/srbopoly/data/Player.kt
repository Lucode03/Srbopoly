package com.example.srbopoly.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.srbopoly.R
import com.example.srbopoly.data.fields.PropertyField

class Player(
    var Username:String,
    var Balance :Int,
    initialPosition:Int=0,
    var Color :String,
    var IsInJail:Boolean=false,
    var Properties:List<PropertyField> = emptyList()
){
    var Position by mutableStateOf(initialPosition)

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