package com.example.srbopoly.ui.screens.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.data.repository.ConnectionStatus
import com.example.srbopoly.ui.dialogs.ChatPanelDialog
import com.example.srbopoly.ui.dialogs.dialogwrappers.ActionResultAnimationWrapper
import com.example.srbopoly.ui.dialogs.dialogwrappers.DiceResultAnimationWrapper
import com.example.srbopoly.ui.dialogs.dialogwrappers.ExitDialogWrapper
import com.example.srbopoly.ui.dialogs.dialogwrappers.GameEndDialogWrapper
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameViewModel,
    gameId: String,
    myId: Int) {

//    var linearBoard by remember { mutableStateOf(false)}

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(gameId) {
        viewModel.joinGame(gameId)
    }

    var showQuitDialog by remember { mutableStateOf(false) }

    val diceResult by viewModel.diceResult.collectAsState()
    val actionResult by viewModel.actionResult.collectAsState()

    val gameState by viewModel.gameState.collectAsState()

    var showPlayerDetails by remember { mutableStateOf(false) }

    val remainingTime by viewModel.remainingTime.collectAsState()

    val gameEndedInfo by viewModel.gameEndedInfo.collectAsState()

    val pauseVotes by viewModel.pauseVotes.collectAsState()
    val gamePaused by viewModel.gamePaused.collectAsState()

    val hasVotedForPause = myId in pauseVotes
    val activePlayerCount = gameState?.players?.count { !it.isBankrupt } ?: 0

    val chatMessages by viewModel.chatMessages.collectAsState()
    val unreadChatCount by viewModel.unreadChatCount.collectAsState()
    var showChatPanel by remember { mutableStateOf(false) }

    val connectionStatus by viewModel.connectionStatus.collectAsState()

    if (connectionStatus == ConnectionStatus.RECONNECTING) {
        Box(
            modifier = Modifier.fillMaxWidth().background(Color(0xFFFFC107)).padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Ponovno povezivanje...", fontSize = 13.sp, color = Color.Black)
        }
    }

    if (showChatPanel) {
        ChatPanelDialog(
            messages = chatMessages,
            myPlayerId = myId,
            onSend = { text -> viewModel.sendChatMessage(text) },
            onDismiss = { showChatPanel = false; viewModel.onChatPanelClosed() }
        )
    }

    LaunchedEffect(gamePaused) {
        if (gamePaused) {
            navController.navigate("home") {
                popUpTo("game/{gameId}") { inclusive = true }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.commandError.collect { error ->
            snackbarHostState.showSnackbar(error)
        }
    }

    GameEndDialogWrapper(gameEndedInfo, gameState) {
        navController.navigate("home") {
            popUpTo("game/{gameId}") { inclusive = true }
        }
    }

    ExitDialogWrapper(showQuitDialog,
        onDismiss = { showQuitDialog=false },
        onYes = {
            showQuitDialog=false
            navController.navigate("home") {
                popUpTo("game/{gameId}") { inclusive = true }
            }
        },
        onNo = { showQuitDialog=false })

    DiceResultAnimationWrapper(diceResult)

    ActionResultAnimationWrapper(actionResult)

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
                .background(Color(0xFFD9F2FA))
        ) {
            Row(
                modifier = Modifier
                    .background(Color(0xFFE7E7E7))
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 6.dp, end = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Napusti igru",
                    modifier = Modifier.size(40.dp).clickable {
                        showQuitDialog = true
                    },
                    Color.Black
                )
                Box(
                    modifier = Modifier.align(Alignment.CenterVertically)
                        .border(
                            border = BorderStroke(2.dp, Color(0xFF001EE7)),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .background(Color(0xFF9EA8FF), shape = RoundedCornerShape(4.dp))
                        .width(60.dp)
                ) {
                    Text(
                        text = "${remainingTime}s",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            remainingTime <= 5 -> Color.Red
                            remainingTime <= 10 -> Color(0xFFDC8A00)
                            else -> Color.Black
                        },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Icon(
                    Icons.Default.AccountBox,
                    contentDescription = "Igraci",
                    modifier = Modifier.size(40.dp).clickable {
                        showPlayerDetails = !showPlayerDetails
                    },
                    Color.Black
                )

                Box {
                    Icon(
                        Icons.Default.MailOutline,
                        contentDescription = "Chat",
                        modifier = Modifier.size(40.dp).clickable {
                            showChatPanel = true
                            viewModel.onChatPanelOpened()
                        },
                        tint = Color.Black
                    )
                    if (unreadChatCount > 0) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.TopEnd)
                                .background(Color.Red, shape = CircleShape)
                        ) {
                            Text(
                                text = unreadChatCount.toString(),
                                fontSize = 10.sp,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier.align(Alignment.CenterVertically)
                        .border(border = BorderStroke(2.dp, if (hasVotedForPause) Color(0xFF00A651) else Color.Gray),
                            shape = RoundedCornerShape(4.dp))
                        .background(if (hasVotedForPause) Color(0xFFB8F2C9) else Color(0xFFE0E0E0), shape = RoundedCornerShape(4.dp))
                        .clickable { viewModel.requestPause() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Pauza (${pauseVotes.size}/$activePlayerCount)",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
//            Icon(
//                if (linearBoard)
//                    Icons.Default.Menu
//                else
//                    Icons.Default.MoreVert,
//                contentDescription = "Promena režima table",
//                modifier = Modifier.size(40.dp).clickable {
//                    linearBoard = !linearBoard
//                },
//                Color.Black
//            )
            }
            Spacer(modifier = Modifier.height((2.dp)))
            HorizontalDivider(
                thickness = 2.dp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Box(modifier = Modifier.fillMaxSize()) {
//            if (linearBoard) {
//                GameLinearView(myId, viewModel, myPlayer!!.Position)
//
//            } else {
                GameBoardView(myId, viewModel, showPlayerDetails)
//            }
            }
        }
    }
}

@Preview
@Composable
fun GmPreview() {
    val mainNavController = rememberNavController()
   // GameScreen(mainNavController, viewModel = GameViewModel(),"", 1)
}