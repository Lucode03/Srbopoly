package com.example.srbopoly.data.fields

import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player

class PropertyField(
    Name:String,
    FieldType:FieldType,
    var Price:Int,
    var Owner:Player?=null,
    var BaseRent:Int,
    var Houses:Int=0,
    var Hotels:Int=0
):Field(Name,FieldType) {
    override fun Action(player: Player,game: Game?)
    {

    }
    fun Buy(player: Player)
    {
        if (Owner == null)
            Owner = player;
        player.Pay(Price);
    }
    fun CalculateRent():Int
    {
        if(Owner==null)
            return 0
        var rent:Int=BaseRent;
        rent+=Houses*30
        rent+=Hotels*50
        return rent;
    }
    fun BuildHouse():Boolean
    {
        if(Houses>3)
            return false
        Houses+=1
        return false;
    }
    fun BuildHotel():Boolean
    {
        if(Houses<3)
            return false
        if(Hotels>2)
            return false
        Hotels+=1
        return false;
    }
}