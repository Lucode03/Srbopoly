package com.example.srbopoly.ui.popups.actions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.srbopoly.data.fields.PropertyField

@Composable
fun PropertyFieldAction(
    field: PropertyField,
    action: Boolean = false,
    onResult: (Boolean) -> Unit,
    modifier: Modifier,
    isMyTurn: Boolean,
    playerID: Int,
    isTurnActionsPhase: Boolean = false
    )
{
    val hasOwner = field.ownerId != null

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        when {
            action && !hasOwner -> {
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { onResult(true) }, enabled = isMyTurn) { Text("Kupi") }
                    Button(onClick = { onResult(false) }, enabled = isMyTurn) { Text("Otkaži") }
                }
            }
            !action && hasOwner && field.ownerId == playerID && isTurnActionsPhase -> {
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { onResult(true) },
                        enabled = isMyTurn && field.houseCount < 5
                    ) {
                        Text(if (field.houseCount == 4) "Izgradi hotel" else "Izgradi kuću")
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