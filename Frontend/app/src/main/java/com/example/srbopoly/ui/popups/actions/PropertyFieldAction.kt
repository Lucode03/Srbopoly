package com.example.srbopoly.ui.popups.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.srbopoly.data.fields.PropertyField
import com.example.srbopoly.data.gamedto.housePrice
import com.example.srbopoly.data.gamedto.mortgageValue
import com.example.srbopoly.data.gamedto.unmortgageCost

@Composable
fun PropertyFieldAction(
    field: PropertyField,
    action: Boolean = false,
    onResult: (Boolean) -> Unit,
    modifier: Modifier,
    isMyTurn: Boolean,
    playerID: Int,
    isTurnActionsPhase: Boolean = false,
    myMoney: Int = 0,
    onBuildHouse: () -> Unit = {},
    onSellHouse: () -> Unit = {},
    onMortgage: () -> Unit = {},
    onUnmortgage: () -> Unit = {}
    )
{
    val hasOwner = field.ownerId != null
    val isMine = field.ownerId == playerID

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        when {
            action && !hasOwner -> {
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { onResult(true) }, enabled = isMyTurn && myMoney >= field.Price) { Text("Kupi") }
                    Button(onClick = { onResult(false) }, enabled = isMyTurn) { Text("Otkaži") }
                }
            }
            !action && isMine && isTurnActionsPhase && !field.isMortgaged -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = onBuildHouse,
                            enabled = isMyTurn && field.houseCount < 5 && myMoney >= field.housePrice
                        ) {
                            Text(if (field.houseCount == 4) "Hotel (${field.housePrice})" else "Kuća (${field.housePrice})")
                        }
                        Button(
                            onClick = onSellHouse,
                            enabled = isMyTurn && field.houseCount > 0
                        ) {
                            Text("Prodaj kuću (+${field.housePrice / 2})")
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = onMortgage,
                            enabled = isMyTurn && field.houseCount == 0
                        ) {
                            Text("Hipotekuj (+${field.mortgageValue})")
                        }
                        Button(onClick = { onResult(false) }, enabled = isMyTurn) { Text("Zatvori") }
                    }
                }
            }
            !action && isMine && isTurnActionsPhase && field.isMortgaged -> {
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onUnmortgage,
                        enabled = isMyTurn && myMoney >= field.unmortgageCost
                    ) {
                        Text("Otkupi hipoteku (${field.unmortgageCost})")
                    }
                    Button(onClick = { onResult(false) }, enabled = isMyTurn) { Text("Zatvori") }
                }
            }
            else -> {
                Button(onClick = { onResult(false) }, enabled = isMyTurn) { Text("Zatvori") }
            }
        }
    }
}