package com.example.srbopoly.data

class SurpriseCard(
    CardName:String,
    Description:String,
    var Amount:Int,
    var Type:String
):Card(CardName,Description) {
    override fun Apply(player:Player)
    {
        //Position -> postavi igraca na polje Amount
        //Movement -> pomeriti igraca za Amount polja
        //Balance -> dodati igracu Amount para
        if(Type=="Position")
        {
            player.SetPosition(Amount)
        }
        else if(Type=="Movement")
        {
            player.Move(Amount)
        }
        else if(Type=="Balance")
        {
            player.Receive(Amount)
        }
    }
}