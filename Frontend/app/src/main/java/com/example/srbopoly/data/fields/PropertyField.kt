package com.example.srbopoly.data.fields

import com.example.srbopoly.R
import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player
import com.example.srbopoly.enums.FieldType

class PropertyField(
    Name:String,
    FieldType:FieldType,
    var Price:Int,
    var Owner:Player?=null,
    var BaseRent:Int,
    var Houses:Int=0,
    var Hotels:Int=0
):Field(Name,FieldType) {
    override fun Action(player: Player,game: Game?):String
    {
        if(Owner==null)
        {
            player.Pay(Price)
            Owner=player
            Owner!!.Properties[FieldType] = Owner!!.Properties[FieldType]!! + 1

            return "Igrač ${player.Username} je kupio nekretninu: ${Name}"
        }
        else
        {
            if(player.id==Owner!!.id)
                return ""
            val rent=CalculateRent()
            player.Pay(rent)
            Owner!!.Receive(rent)

            return "Igrač ${player.Username} je morao da plati ${rent} \uD83D\uDCB5 vlasniku: ${Owner!!.Username}"
        }
    }
    fun CalculateRent():Int
    {
        if(Owner==null)
            return 0
        var rent:Int=BaseRent;
        rent+=Houses*30
        rent+=Hotels*65
        return rent;
    }
    fun CheckMonopoly():Boolean
    {
        if (Owner==null)
            return false
        return Owner!!.CheckMonopoly(FieldType)
    }
    fun CheckStructureToBuild():Boolean
    {
        if(Houses<3)
            return false
        else
            return true
    }
    fun BuildHouse():Boolean
    {
        if(Owner==null)
            return false
        if(Houses==3)
            return false


        val price=GetHousePrice()
        Owner!!.Pay(price)
        Houses+=1

        return true;
    }
    fun GetHousePrice():Int
    {
        return (100+BaseRent/10).toInt()
    }
    fun BuildHotel():Boolean
    {
        if(Owner==null)
            return false

        if(Houses<3)
            return false
        if(Hotels==2)
            return false

        val price=GetHotelPrice()
        Owner!!.Pay(price)
        Hotels+=1
        return true;
    }
    fun GetHotelPrice():Int
    {
        return (150+BaseRent/8).toInt()
    }
}