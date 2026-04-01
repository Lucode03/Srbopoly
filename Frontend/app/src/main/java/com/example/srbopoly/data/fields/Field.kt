package com.example.srbopoly.data.fields

import com.example.srbopoly.R
import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player

abstract class Field
(
    var Name: String,
    var FieldType: FieldType,
    var GameFieldID: Int = 0
    )
{
    abstract fun Action(player: Player,game: Game?)
    fun getPosition(): Pair<Int, Int> {
        return when (GameFieldID) {
            in 0..10 -> 0 to GameFieldID                     // gore
            in 11..19 -> (GameFieldID - 10) to 10           // desno
            in 20..30 -> 10 to (30 - GameFieldID)           // dole
            in 31..39 -> (40 - GameFieldID) to 0            // levo
            else -> 0 to 0
        }
    }
}
fun getFieldImage(fieldType:FieldType):Int {
    return when (fieldType) {
        FieldType.START-> R.drawable.start_field
        FieldType.JAIL ->R.drawable.jail_field
        FieldType.PARKING->R.drawable.parking_field
        FieldType.GO_TO_JAIL->R.drawable.go_to_jail_field
    
        FieldType.REWARD->R.drawable.reward_field
        FieldType.CHANCE->R.drawable.chance_field
        FieldType.NATIONAL_PARK->R.drawable.national_park_field
        FieldType.TAX->R.drawable.tax_field
        FieldType.ELECTRIC_TAX->R.drawable.electro_field
        FieldType.WATER_TAX->R.drawable.electro_field
    
        FieldType.KIM->R.drawable.brown_field
        FieldType.ZAPADNA_SRBIJA->R.drawable.dark_blue_field
        FieldType.PREMIUM1->R.drawable.yellow_field
        FieldType.VOJVODINA->R.drawable.green_field
        FieldType.JUZNA_SRBIJA->R.drawable.light_blue_field
        FieldType.SUMADIJA->R.drawable.orange_field
        FieldType.ISTOCNA_SRBIJA->R.drawable.pink_field
        FieldType.PREMIUM2->R.drawable.red_field
    }
}
enum class FieldType {
    START, JAIL, PARKING, GO_TO_JAIL,
    REWARD, CHANCE, NATIONAL_PARK, TAX,
    ELECTRIC_TAX, WATER_TAX,
    VOJVODINA, ISTOCNA_SRBIJA, JUZNA_SRBIJA, KIM,
    SUMADIJA, ZAPADNA_SRBIJA, PREMIUM1, PREMIUM2
}