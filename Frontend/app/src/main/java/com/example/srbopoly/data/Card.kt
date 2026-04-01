package com.example.srbopoly.data

abstract class Card(
    var CardName:String,
    var Description:String,
    var GameCardID:Int = 0
) {
    abstract fun Apply(player:Player)
}