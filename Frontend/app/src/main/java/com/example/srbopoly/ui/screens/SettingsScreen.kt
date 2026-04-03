package com.example.srbopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.classes.getDiceImage
import com.example.srbopoly.data.getFigure
import com.example.srbopoly.ui.dialogs.ExitDialog
import com.example.srbopoly.viewmodels.GameViewModel
import com.example.srbopoly.viewmodels.LobbyViewModel

@Composable
fun SettingsScreen(navController: NavController,myId: Int=1, gameCode: String, lobbyViewModel: LobbyViewModel = hiltViewModel()) {

    val colors = listOf("Crvena", "Plava", "Zelena", "Žuta", "Narandžasta", "Bela")

    var maxMovesText by remember { mutableStateOf(lobbyViewModel.maxMoves.value.toString()) }

    val maxMoves by lobbyViewModel.maxMoves.collectAsState()
    val lobby by lobbyViewModel.currentLobby.collectAsState()
    val dice1 by lobbyViewModel.dice1.collectAsState()
    val dice2 by lobbyViewModel.dice2.collectAsState()

    val myPlayer = lobby?.players?.find { it.userId == myId }
    val isHost = lobby?.hostUserId == myId

    var showExitDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(gameCode) {
        lobbyViewModel.initLobby(gameCode, myId)
    }

    LaunchedEffect(lobby) {
        val players = lobby?.players ?: emptyList()
        if (players.size >= 2 && players.all { it.isReady }) {
            navController.navigate("game") {
                popUpTo("settings") { inclusive = true }
            }
        }
    }

    if (showExitDialog) {
        ExitDialog(
            onDismiss = {showExitDialog=false},
            onYes = {
                showExitDialog=false

                lobbyViewModel.leaveLobby(gameCode,  myId)
                navController.navigate("home")
            },
            onNo = {showExitDialog=false},
            text = "Izgubićete sva podešavanja za ovu igru!"
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {

        Box {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Izlaz",
                modifier = Modifier.clickable { showExitDialog = true  }
                    .align(Alignment.CenterStart)
            )

            Text(
                text = "Podešavanja",
                fontSize = 22.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Kod igre: $gameCode",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color=Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text="Boja figurice:",
            fontSize = 16.sp)
        colors.forEachIndexed { index, colorName ->
            val isTaken = lobby?.players?.any { it.color == index && it.userId != myId } ?: false

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(if (isTaken) 0.5f else 1f)
            ) {
                RadioButton(
                    selected = myPlayer?.color == index,
                    onClick = { if (!isTaken) lobbyViewModel.setPlayerColor(gameCode, myId, index) },
                    enabled = !isTaken
                )
                Image(
                    painter = painterResource(getFigure(colorName)),
                    contentDescription = colorName,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = colorName, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (myPlayer != null) {
            if(isHost)

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
                                    lobbyViewModel.setMaxMoves(number)
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        maxLines = 1,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            else{
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text="Maksimalan broj poteza: $maxMovesText",
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        myPlayer?.let { player ->
            Box(modifier = Modifier.fillMaxWidth()
                .clickable (
                    enabled = maxMoves >= 50 && myPlayer.rolledNumber==0,
                    onClick = { lobbyViewModel.rollDice(gameCode, myId) }
                ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(getDiceImage(dice1)),
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
                        painter = painterResource(getDiceImage(dice2)),
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
                text = if(player.rolledNumber==0)
                    "Bacite kockice"
                    else
                        "Zbir: ${player.rolledNumber}",
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
                enabled = myPlayer?.rolledNumber!=0,
                onClick = {
//                    lobbyViewModel.toggleReady(gameCode,myId)
                navController.navigate("game") {
                    popUpTo("settings") { inclusive = true }
                }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (myPlayer?.isReady == true) Color.Green else Color.Gray
                ),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(if (myPlayer?.isReady == true) "Spreman" else "Nisam spreman")
            }
        }

        lobby?.players?.forEach { player ->
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text=player.username,
                    color= if(myId==player.id) Color.Blue else Color.Black,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(getFigure(colors[player.color])),
                    contentDescription = colors[player.color],
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if(player.rolledNumber == 0)
                        "Baca kockice"
                    else
                        "Zbir: ${player.rolledNumber}",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (player.isReady) "Spreman" else "Čeka...",
                    color = if (player.isReady) Color.Green else Color.Gray
                )

                Spacer(modifier = Modifier.width(8.dp))

                if (player.userId == lobby?.hostUserId){
                    Text(" 👑",
                        fontSize = 16.sp,
                        color = Color.Yellow)
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    val mainNavController = rememberNavController()
    SettingsScreen(mainNavController, gameCode = "123456")
}