package com.example.srbopoly.ui.screens.game

import android.graphics.Rect
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.R
import com.example.srbopoly.classes.getDiceImage
import com.example.srbopoly.data.fields.getCenterRect
import com.example.srbopoly.data.fields.getFieldImage
import com.example.srbopoly.data.fields.getFieldOffset
import com.example.srbopoly.data.fields.getFieldSize
import com.example.srbopoly.data.fields.getRect
import com.example.srbopoly.data.getFigure
import com.example.srbopoly.draw_functions.drawImageOnCanvas
import com.example.srbopoly.draw_functions.rotateImageBitmap
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameBoardView(myId:Int,viewModel: GameViewModel,playerPosition:Int)
{
    val board_center_image = ImageBitmap.imageResource(R.drawable.board_center)

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
        ) {
            val maxWidth = maxWidth
            Box(modifier = Modifier.size(maxWidth)) {
                board.forEach { field ->

                    val (width, height) = getFieldSize(field.GameFieldID, maxWidth)
                    val hasRowFigures = (field.GameFieldID in 10..19 || field.GameFieldID in 30..39)
                    val (x, y) = getFieldOffset(field.GameFieldID, maxWidth)

                    val playersOnField = playersByField[field.GameFieldID] ?: emptyList()

                    val originalImage = ImageBitmap.imageResource(id = getFieldImage(field.FieldType))
                    val rotatedImage = when (field.GameFieldID) {
                        in 1..9 -> rotateImageBitmap(originalImage, 180f)
                        in 11..19 -> rotateImageBitmap(originalImage, -90f)
                        in 31..39 -> rotateImageBitmap(originalImage, 90f)
                        else -> originalImage
                    }
                    Box(
                        modifier = Modifier
                            .offset(x = x, y = y)
                            .size(width, height)
                    ) {

                        Image(
                            modifier = Modifier.fillMaxSize(),
                            bitmap = rotatedImage,
                            contentDescription = "Polje ${field.GameFieldID}",
                            contentScale = ContentScale.FillBounds
                        )

                        val figureSize=
                            if(playersOnField.size<3)
                                max(width,height) / 3f
                            else if(playersOnField.size==3)
                                max(width,height) / 4f
                            else
                                max(width,height) / 4.5f

                        if (hasRowFigures) {
                            Row(
                                modifier = Modifier
                                    .align(
                                        when (field.GameFieldID) {
                                            in (10..19) -> Alignment.BottomCenter
                                            in (30..39) -> Alignment.TopCenter
                                            else -> Alignment.CenterEnd
                                        }
                                    )
                                    .padding(2.dp)
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
                            val columns = if (playersOnField.size <= 3) {
                                listOf(playersOnField)
                            } else {
                                playersOnField.chunked((playersOnField.size + 1) / 2)
                            }

                            Row(
                                modifier = Modifier
                                    .align(
                                        when (field.GameFieldID) {
                                            in (0..9) -> Alignment.CenterEnd
                                            in (20..29) -> Alignment.CenterStart
                                            else -> Alignment.CenterEnd
                                        }
                                    )
                                    .padding(2.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                columns.forEach { columnPlayers ->
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        columnPlayers.forEach { player ->
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
                Canvas(modifier = Modifier.matchParentSize()) {

                    val centerRect= getCenterRect(size)
                    drawImageOnCanvas(board_center_image,centerRect)

                    drawRect(
                        color = Color.Black,
                        topLeft = centerRect.topLeft,
                        size = centerRect.size,
                        style = Stroke(width = 2.dp.toPx())
                    )
                    for (field in board) {
                        val rect = getRect(field.GameFieldID, size)
                        val isMyField = field.GameFieldID == playerPosition

                        drawRect(
                            color = if (field.GameFieldID == playerPosition) Color.Red else Color.Black,
                            topLeft = rect.topLeft,
                            size = rect.size,
                            style = Stroke(width = if (isMyField) 4f else 2f)
                        )
                    }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center)
                {
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
                            modifier = Modifier
                                .size(64.dp)
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
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(
                                    width = 2.dp,
                                    color = Color.Blue,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )
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