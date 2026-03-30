package com.example.srbopoly.ui.screens.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.classes.getDiceImage
import com.example.srbopoly.data.fields.getFieldOffset
import com.example.srbopoly.data.fields.getFieldSize
import com.example.srbopoly.data.fields.getRect
import com.example.srbopoly.data.getFigure
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameBoardView(myId:Int,viewModel: GameViewModel,playerPosition:Int)
{
    val gameState by viewModel.gameState

    val dice1 by viewModel.dice1.collectAsState()
    val dice2 by viewModel.dice2.collectAsState()

    val board = viewModel.board

    val players by viewModel.players
//    val playersByField = remember(players) {
//        players.groupBy { it.Position }
//    }
    val playersByField = players.groupBy { it.Position }
    Column {
        Spacer(modifier = Modifier.height((15.dp)))

        Column {
            players.forEach { player ->
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()) {
                    val isCurrentPlayer=player.id==gameState.currentPlayerId
                    Image(
                        painter = painterResource(getFigure(player.Color)),
                        contentDescription = "Figurica od ${player.Username}",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text=player.Username,
                        fontSize = 16.sp,
                        color = if (isCurrentPlayer) Color.Blue else Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text="${player.Balance} \uD83D\uDCB5",
                        fontSize = 16.sp,
                        color = if (isCurrentPlayer) Color.Blue else Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text= if (isCurrentPlayer) "Na potezu" else "",
                        fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
                .padding(top=40.dp)
        ) {
            val maxWidth = maxWidth
            Box(modifier = Modifier.size(maxWidth)) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center)
                {
                    Image(
                        painter = painterResource(getDiceImage(dice1)),
                        contentDescription = "Center background",
                        modifier = Modifier.fillMaxSize()
                    )
                    Row(
                        modifier = Modifier.clickable(
                                enabled = myId == gameState.currentPlayerId,
                                onClick = { viewModel.rollDice(myId) }
                        ),
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
                Canvas(modifier = Modifier.matchParentSize()) {

                    for (field in board) {
                        val rect = getRect(field.GameFieldID, size)

                        val isMyField = field.GameFieldID == playerPosition

                        drawRect(
                            color = Color.White,
                            topLeft = rect.topLeft,
                            size = rect.size
                        )

                        drawRect(
                            color = if (field.GameFieldID == playerPosition) Color.Red else Color.Black,
                            topLeft = rect.topLeft,
                            size = rect.size,
                            style = Stroke(width = if (isMyField) 4f else 2f)
                        )
                    }
                }
                board.forEach { field ->

                    val (width, height) = getFieldSize(field.GameFieldID, maxWidth)
                    val isHorizontal = width > height
                    val (x, y) = getFieldOffset(field.GameFieldID, maxWidth)

                    val playersOnField = playersByField[field.GameFieldID] ?: emptyList()

                    Box(
                        modifier = Modifier
                            .offset(x = x, y = y)
                            .size(width, height)
                    ) {

                        Text(
                            text = "${field.GameFieldID}",
                            fontSize = 10.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )

                        val figureSize=max(width,height) / 3.5f

                        if (isHorizontal) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(2.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                playersOnField.forEach { player ->
                                    Image(
                                        painter = painterResource(id = getFigure(player.Color)),
                                        contentDescription = null,
                                        modifier = Modifier.size(figureSize)
                                    )
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .align(
                                        if(field.GameFieldID==10) Alignment.BottomCenter
                                        else if(field.GameFieldID==20) Alignment.CenterStart
                                        else if(field.GameFieldID==30) Alignment.TopCenter
                                        else Alignment.CenterEnd
                                    )
                                    .padding(2.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                playersOnField.forEach { player ->
                                    Image(
                                        painter = painterResource(id = getFigure(player.Color)),
                                        contentDescription = null,
                                        modifier = Modifier.size(figureSize)
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


@Preview
@Composable
fun GffaPreview() {
    val mainNavController = rememberNavController()
    GameBoardView(1, viewModel = GameViewModel(),10)
}