package com.example.srbopoly.data

class RewardCard(
    CardName:String,
    Description:String,
    GameCardID:Int,
    var Reward:Int
):Card(CardName,Description,GameCardID) {
    override fun Apply(player:Player)
    {
        player.Receive(Reward)
    }
}