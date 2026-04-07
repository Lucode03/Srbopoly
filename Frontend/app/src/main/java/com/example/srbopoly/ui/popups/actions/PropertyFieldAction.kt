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
    isMyTurn:Boolean,
    playerID:Int,
)
{
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val hasOwner = field.Owner != null

        if (action) {
            if (!hasOwner) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        onResult(true)
                    },
                        enabled = isMyTurn
                    ) {
                        Text("Kupi")
                    }
                    Button(onClick = {
                        onResult(false)
                    },
                        enabled = isMyTurn
                    ) {
                        Text("Otkaži")
                    }
                }
            } else {
                Button(onClick = {
                    onResult(true)
                },
                    enabled = isMyTurn
                ) {
                    Text("Plati")
                }
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isMyTurn && hasOwner) {
                    if (playerID == field.Owner!!.id) {
                        //true-hotel  false-kuca
                        val structureToBuild = field.CheckStructureToBuild()

                        Button(
                            onClick = {
                                if (structureToBuild)
                                    field.BuildHotel()
                                else
                                    field.BuildHouse()
                                onResult(false)
                            },
                            enabled = field.CheckMonopoly()
                        ) {
                            Text(if (structureToBuild) "Izgradi hotel" else "Izgradi kuću")
                        }
                    }
                }
                Button(
                    onClick = {
                        onResult(false)
                    },
                    enabled = isMyTurn
                ) {
                    Text("Zatvori")
                }
            }
        }
    }
}