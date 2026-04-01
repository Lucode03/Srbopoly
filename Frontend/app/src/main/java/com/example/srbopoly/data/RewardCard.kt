package com.example.srbopoly.data

class RewardCard(
    CardName:String,
    Description:String,
    var Reward:Int
):Card(CardName,Description) {
    override fun Apply(player:Player)
    {
        player.Receive(Reward)
    }
}