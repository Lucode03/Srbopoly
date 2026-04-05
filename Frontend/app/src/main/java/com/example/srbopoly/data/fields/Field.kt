package com.example.srbopoly.data.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.srbopoly.R
import com.example.srbopoly.data.Game
import com.example.srbopoly.data.Player
import com.example.srbopoly.ui.popups.actions.BonusFieldAction
import com.example.srbopoly.ui.popups.actions.JailFieldAction
import com.example.srbopoly.ui.popups.actions.MovementFieldAction
import com.example.srbopoly.ui.popups.actions.NationalParkFieldAction
import com.example.srbopoly.ui.popups.actions.PaymentFieldAction
import com.example.srbopoly.ui.popups.actions.PropertyFieldAction
import com.example.srbopoly.ui.popups.actions.RewardCardFieldAction
import com.example.srbopoly.ui.popups.actions.SurpriseCardFieldAction
import com.example.srbopoly.ui.popups.views.BonusFieldView
import com.example.srbopoly.ui.popups.views.JailFieldView
import com.example.srbopoly.ui.popups.views.MovementFieldView
import com.example.srbopoly.ui.popups.views.NationalParkFieldView
import com.example.srbopoly.ui.popups.views.PaymentFieldView
import com.example.srbopoly.ui.popups.views.PropertyFieldView
import com.example.srbopoly.ui.popups.views.RewardCardFieldView
import com.example.srbopoly.ui.popups.views.SurpriseCardFieldView

abstract class Field
(
    var Name: String,
    var FieldType: FieldType,
    var GameFieldID: Int = 0
    )
{
    abstract fun Action(player: Player,game: Game?=null)

    companion object{
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
                FieldType.WATER_TAX->R.drawable.water_field

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
        fun getType(fieldType: FieldType):String
        {
            return when(fieldType){
                FieldType.NATIONAL_PARK->"Nacionalni park"

                FieldType.KIM->"Kosovo i Metohija"
                FieldType.ZAPADNA_SRBIJA->"Zapadna Srbija"
                FieldType.PREMIUM1->"Premijum lokacija 1"
                FieldType.VOJVODINA->"Vojvodina"
                FieldType.JUZNA_SRBIJA->"Južna Srbija"
                FieldType.SUMADIJA->"Šumadija"
                FieldType.ISTOCNA_SRBIJA->"Istočna Srbija"
                FieldType.PREMIUM2->"Premijum lokacija 2"
                else -> ""
            }
        }
    }
}

@Composable
fun FieldInfo(field: Field) {
    when (field) {
        is BonusField -> BonusFieldView(field)
        is JailField -> JailFieldView(field)
        is MovementField -> MovementFieldView(field)
        is NationalParkField -> NationalParkFieldView(field)
        is PaymentField -> PaymentFieldView(field)
        is PropertyField -> PropertyFieldView(field)
        is RewardCardField -> RewardCardFieldView(field)
        is SurpriseCardField -> SurpriseCardFieldView(field)
    }
}
@Composable
fun FieldAction(field: Field,
                action:Boolean,
                isMyTurn:Boolean,
                modifier: Modifier = Modifier,
                onResult: (Boolean) -> Unit)
{
    val fullWidthModifier = modifier.fillMaxWidth()

    when (field) {
        is BonusField -> BonusFieldAction(field,action,onResult,fullWidthModifier,isMyTurn)
        is JailField -> JailFieldAction(field,action,onResult,fullWidthModifier,isMyTurn)
        is MovementField -> MovementFieldAction(field,action,onResult,fullWidthModifier,isMyTurn)
        is NationalParkField -> NationalParkFieldAction(field,action,onResult,fullWidthModifier,isMyTurn)
        is PaymentField -> PaymentFieldAction(field,action,onResult,fullWidthModifier,isMyTurn)
        is PropertyField -> PropertyFieldAction(field,action,onResult,fullWidthModifier,isMyTurn)
        is RewardCardField -> RewardCardFieldAction(field,action,onResult,fullWidthModifier,isMyTurn)
        is SurpriseCardField -> SurpriseCardFieldAction(field,action,onResult,fullWidthModifier,isMyTurn)
    }
}

enum class FieldType {
    START, JAIL, PARKING, GO_TO_JAIL,
    REWARD, CHANCE, NATIONAL_PARK, TAX,
    ELECTRIC_TAX, WATER_TAX,
    VOJVODINA, ISTOCNA_SRBIJA, JUZNA_SRBIJA, KIM,
    SUMADIJA, ZAPADNA_SRBIJA, PREMIUM1, PREMIUM2
}