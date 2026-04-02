package com.example.srbopoly.ui.screens.game

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.data.RewardCard
import com.example.srbopoly.data.SurpriseCard
import com.example.srbopoly.ui.dialogs.ExitDialog
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameScreen(navController: NavController,viewModel: GameViewModel,myId: Int=1) {

//    val fields = remember { mutableStateListOf<Field>() }
    val rewardCardsDeck = remember { mutableStateListOf<RewardCard>() }
    val surpriseCardsDeck = remember { mutableStateListOf<SurpriseCard>() }

    val players by viewModel.players
    val myPlayer = remember {players.find{it.id==myId}}
//    LaunchedEffect(Unit) {
//        fields.addAll(createFields())
//        rewardCardsDeck.addAll(createRewardCards())
//        surpriseCardsDeck.addAll(createSurpriseCards())
//    }

    var linearBoard by remember { mutableStateOf(true)}

    var showQuitDialog by remember { mutableStateOf(false) }
    if (showQuitDialog) {
        ExitDialog(
            onDismiss = {showQuitDialog=false},
            onYes = {
                showQuitDialog=false
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
                    myPlayer?.Move(1)
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
        Spacer(modifier = Modifier.height((2.dp)))
        HorizontalDivider(thickness = 2.dp, color = Color.Black, modifier = Modifier.fillMaxWidth())
        Box(modifier = Modifier.fillMaxSize()) {
            if (linearBoard) {
                GameLinearView(myId, viewModel, myPlayer!!.Position)

            } else {
                GameBoardView(myPlayer!!,viewModel)
            }
        }
    }
}

@Preview
@Composable
fun GmPreview() {
    val mainNavController = rememberNavController()
    GameScreen(mainNavController, viewModel = GameViewModel())
}