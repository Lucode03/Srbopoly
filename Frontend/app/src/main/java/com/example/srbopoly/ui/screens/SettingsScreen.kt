package com.example.srbopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.classes.getDiceImage
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun SettingsScreen(navController: NavController,viewModel: GameViewModel,myId: Int=1) {

    val colors = listOf("Crvena", "Plava", "Zelena", "Žuta", "Bela", "Crna")

    var maxMovesText by remember { mutableStateOf(viewModel.gameState.value.maxMoves.toString()) }

    val players by viewModel.players
    val myPlayer = players.find { it.id == myId }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text="Podešavanja",
            fontSize = 22.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
            )

        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color=Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text="Boja figurice:",
            fontSize = 16.sp)
        colors.forEach { color ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = myPlayer?.color == color,
                    onClick = { viewModel.setPlayerColor(myId,color) }
                )

                Text(color)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text="Maksimalan broj poteza:",
                fontSize = 16.sp
            )
            OutlinedTextField(
                value = maxMovesText,
                onValueChange = {input ->
                    if (input.all { it.isDigit() }) {
                        maxMovesText = input

                        val number = input.toIntOrNull()
                        if (number != null) {
                            viewModel.setMaxMoves(number)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = Modifier.padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(16.dp))

        myPlayer?.let { player ->
            Box(modifier = Modifier.fillMaxWidth()
                .clickable (
                    enabled = viewModel.gameState.value.maxMoves >= 50 && myPlayer.diceResult==0,
                    onClick = { viewModel.rollDice(myId) }
                ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(getDiceImage(player.dice1)),
                        contentDescription = "Dice 1",
                        modifier = Modifier.size(64.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 2.dp,
                                color = Color.Blue,
                                shape = RoundedCornerShape(12.dp)
                            )
                    )
                    Image(
                        painter = painterResource(getDiceImage(player.dice2)),
                        contentDescription = "Dice 2",
                        modifier = Modifier.size(64.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 2.dp,
                                color = Color.Blue,
                                shape = RoundedCornerShape(12.dp)
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Zbir: ${player.diceResult}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }


        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color=Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth())
        {
            Button(
                enabled = myPlayer?.diceResult!=0,
                onClick = {
                navController.navigate("game") {
                    popUpTo("settings") { inclusive = true }
                }
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Pokreni igru")
            }
        }

    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    val mainNavController = rememberNavController()
    SettingsScreen(mainNavController, viewModel = viewModel())
}