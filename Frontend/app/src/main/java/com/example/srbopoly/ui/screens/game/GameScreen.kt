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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.data.RewardCard
import com.example.srbopoly.data.SurpriseCard
import com.example.srbopoly.ui.animations.DiceResultAnimation
import com.example.srbopoly.ui.dialogs.ExitDialog
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameScreen(navController: NavController,viewModel: GameViewModel,myId: Int=1) {

//    var linearBoard by remember { mutableStateOf(false)}

    var showQuitDialog by remember { mutableStateOf(false) }

    val diceResult by viewModel.diceResult.collectAsState()

    var showPlayerDetails by remember { mutableStateOf(false) }

    val remainingTime by viewModel.remainingTime

    LaunchedEffect(Unit) {
        viewModel.startTurn()
    }
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
    if (diceResult != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .zIndex(10f)
            ,
            contentAlignment = Alignment.Center
        ) {
            DiceResultAnimation(diceResult!!)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFFD9F2FA))
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFE7E7E7))
                .fillMaxWidth()
                .padding(top=8.dp, start = 6.dp, end = 6.dp),
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
            Box(
                modifier = Modifier.align(Alignment.CenterVertically)
                    .border(border = BorderStroke(2.dp,Color(0xFF001EE7)), shape = RoundedCornerShape(4.dp))
                    .background(Color(0xFF9EA8FF), shape = RoundedCornerShape(4.dp))
                    .width(60.dp)
            ){
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
                    showPlayerDetails=!showPlayerDetails
                },
                Color.Black
            )
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
        HorizontalDivider(thickness = 2.dp, color = Color.Black, modifier = Modifier.fillMaxWidth())
        Box(modifier = Modifier.fillMaxSize()) {
//            if (linearBoard) {
//                GameLinearView(myId, viewModel, myPlayer!!.Position)
//
//            } else {
                GameBoardView(myId,viewModel,showPlayerDetails)
//            }
        }
    }
}

@Preview
@Composable
fun GmPreview() {
    val mainNavController = rememberNavController()
    GameScreen(mainNavController, viewModel = GameViewModel(),10)
}