package com.example.srbopoly.data

abstract class Card(
    var CardName:String,
    var Description:String,
    var GameCardID:Int
) {
    abstract fun Apply(player:Player)
}