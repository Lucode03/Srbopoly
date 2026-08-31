package com.example.srbopoly.ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.srbopoly.data.fields.PropertyField
import com.example.srbopoly.data.gamedto.PlayerDto
import com.example.srbopoly.data.gamedto.TradeOfferDto

@Composable
fun TradeProposalDialog(
    myId: Int,
    myProperties: List<PropertyField>,
    otherPlayers: List<PlayerDto>,
    allProperties: List<PropertyField>,
    onPropose: (targetId: Int, offer: TradeOfferDto) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedTarget by remember { mutableStateOf<PlayerDto?>(null) }
    val selectedMyFields = remember { mutableStateListOf<Int>() }
    val selectedTheirFields = remember { mutableStateListOf<Int>() }
    var offeredMoney by remember { mutableStateOf("0") }
    var requestedMoney by remember { mutableStateOf("0") }

    Dialog(onDismissRequest = onDismiss) {
        Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(12.dp)) {
            Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                Text("Predloži razmenu", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                if (selectedTarget == null) {
                    Text("Izaberi igrača:")
                    otherPlayers.forEach { p ->
                        Text(
                            p.name,
                            modifier = Modifier.fillMaxWidth().padding(8.dp)
                                .clickable { selectedTarget = p }
                        )
                    }
                } else {
                    val target = selectedTarget!!
                    val theirProperties = allProperties.filter { it.ownerId == target.id }

                    Text("Razmena sa: ${target.name}", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Tvoja ponuda:")
                    myProperties.forEach { field ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = field.GameFieldID in selectedMyFields,
                                onCheckedChange = {
                                    if (it) selectedMyFields.add(field.GameFieldID)
                                    else selectedMyFields.remove(field.GameFieldID)
                                }
                            )
                            Text(field.Name)
                        }
                    }
                    OutlinedTextField(
                        value = offeredMoney, onValueChange = { offeredMoney = it },
                        label = { Text("Novac koji nudiš") }, modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Tražiš od ${target.name}:")
                    theirProperties.forEach { field ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = field.GameFieldID in selectedTheirFields,
                                onCheckedChange = {
                                    if (it) selectedTheirFields.add(field.GameFieldID)
                                    else selectedTheirFields.remove(field.GameFieldID)
                                }
                            )
                            Text(field.Name)
                        }
                    }
                    OutlinedTextField(
                        value = requestedMoney, onValueChange = { requestedMoney = it },
                        label = { Text("Novac koji tražiš") }, modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = {
                            onPropose(
                                target.id,
                                TradeOfferDto(
                                    offeredPropertyIds = selectedMyFields.toList(),
                                    offeredMoney = offeredMoney.toIntOrNull() ?: 0,
                                    requestedPropertyIds = selectedTheirFields.toList(),
                                    requestedMoney = requestedMoney.toIntOrNull() ?: 0
                                )
                            )
                            onDismiss()
                        }) { Text("Pošalji ponudu") }
                        Button(onClick = onDismiss) { Text("Otkaži") }
                    }
                }
            }
        }
    }
}