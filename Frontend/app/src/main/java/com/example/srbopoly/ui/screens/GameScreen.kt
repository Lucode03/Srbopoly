package com.example.srbopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.data.RewardCard
import com.example.srbopoly.data.SurpriseCard
import com.example.srbopoly.data.fields.JailField
import com.example.srbopoly.data.getFigure
import com.example.srbopoly.ui.dialogs.ExitDialog
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameScreen(navController: NavController,viewModel: GameViewModel) {

    val gameState by viewModel.gameState

//    val fields = remember { mutableStateListOf<Field>() }
    val rewardCardsDeck = remember { mutableStateListOf<RewardCard>() }
    val surpriseCardsDeck = remember { mutableStateListOf<SurpriseCard>() }
    val board = List(40) { index ->
        JailField("Polje $index",index)
    }
    val players by viewModel.players
    val myPlayer=players.first()
//    LaunchedEffect(Unit) {
//        fields.addAll(createFields())
//        rewardCardsDeck.addAll(createRewardCards())
//        surpriseCardsDeck.addAll(createSurpriseCards())
//    }

    var linearBoard by remember { mutableStateOf(false)}

    var showQuitDialog by remember { mutableStateOf(false) }
    if (showQuitDialog) {
        ExitDialog(
            onDismiss = {showQuitDialog=false},
            onYes = {
                showQuitDialog=false
                viewModel.resetGameSettings()
                navController.navigate("home") {
                    popUpTo("game") { inclusive = true }
                }
            },
            onNo = {showQuitDialog=false},
            text = "Igra može biti nastavljena samo ukoliko svi ostali igrači ponovo uđu!"
        )
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Napusti igru",
                modifier = Modifier.size(40.dp).clickable {
                    showQuitDialog = true
                },
                Color.Black
            )
            Icon(
                Icons.Default.Edit,
                contentDescription = "Pomeraj",
                modifier = Modifier.size(40.dp).clickable {
                    myPlayer.Move(1)
                },
                Color.Black
            )
            Icon(
                if (linearBoard)
                    Icons.Default.Menu
                else
                    Icons.Default.MoreVert,
                contentDescription = "Promena režima table",
                modifier = Modifier.size(40.dp).clickable {
                    linearBoard = !linearBoard
                },
                Color.Black
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            val playersByField = players.groupBy { it.Position }
            val playerPosition=myPlayer.Position

            if (linearBoard) {

                val visibleFields = remember(playerPosition, board) {
                    (-1..10).map { offset ->
                        val index = (playerPosition + offset + board.size) % board.size
                        board[index]
                    }
                }

                val listState = rememberLazyListState()
                LaunchedEffect(playerPosition) {
                    listState.scrollToItem(1)
                }


                LazyColumn(state = listState,
                    modifier = Modifier.align(Alignment.TopCenter)
                        .padding(5.dp)
                ) {
                    items(visibleFields) { field ->
                        val playersOnField = playersByField[field.GameFieldID] ?: emptyList()
                        val isMyField=field.GameFieldID == playerPosition
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(65.dp)
                                .width(200.dp)
                                .border(
                                    if (isMyField) 2.dp else 1.dp,
                                    if (isMyField) Color.Red else Color.Black
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.fillMaxSize()
                                .padding(10.dp))
                            {
                                Text(
                                    text="${field.GameFieldID}",
                                    textAlign = TextAlign.Center)
                            }
                            Box(modifier = Modifier.size(40.dp)
                                .align(Alignment.BottomEnd)
                                .padding(10.dp))
                            {
                                //ciji je property field
                            }
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(2.dp)
                            ) {
                                playersOnField.chunked(3).forEach { rowPlayers ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        rowPlayers.forEach { player ->
                                            Image(
                                                painter = painterResource(id = getFigure(player.Color)),
                                                contentDescription = player.Username,
                                                modifier = Modifier
                                                    .size(24.dp)
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            } else {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    val cellSize = maxWidth / 11

                    Box(modifier = Modifier.size(maxWidth)) {
                        for (field in board) {
                            val (row, col) = field.getPosition()
                            val playersOnField = playersByField[field.GameFieldID] ?: emptyList()

                            val isMyField=field.GameFieldID == playerPosition
                            Box(
                                modifier = Modifier
                                    .offset(x = col * cellSize, y = row * cellSize)
                                    .size(cellSize)
                                    .border(if (isMyField) 2.dp else 1.dp,
                                        if (isMyField) Color.Red else Color.Black
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("${field.GameFieldID}", fontSize = 10.sp)
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(2.dp)
                                ) {
                                    playersOnField.chunked(3).forEach { rowPlayers ->
                                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                            rowPlayers.forEach { player ->
                                                Image(
                                                    painter = painterResource(id = getFigure(player.Color)),
                                                    contentDescription = "Figure of ${player.Username}",
                                                    modifier = Modifier
                                                        .size(cellSize / 4)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun GmPreview() {
    val mainNavController = rememberNavController()
    GameScreen(mainNavController, viewModel = viewModel())
}