package com.example.srbopoly.ui.screens.game

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.srbopoly.classes.getDiceImage
import com.example.srbopoly.data.getFigure
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameLinearView(myId:Int, viewModel: GameViewModel, playerPosition:Int)
{
    val gameState by viewModel.gameState

    val dice1 by viewModel.dice1.collectAsState()
    val dice2 by viewModel.dice2.collectAsState()

    val board = viewModel.board

    val players by viewModel.players
    val playersByField = players.groupBy { it.Position }
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


    Box(modifier = Modifier.fillMaxSize())
    {
        LazyColumn(
            state = listState,
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(5.dp)
        ) {
            items(visibleFields) { field ->
                val playersOnField = playersByField[field.GameFieldID] ?: emptyList()
                val isMyField = field.GameFieldID == playerPosition
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
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(10.dp)
                    )
                    {
                        Text(
                            text = "${field.GameFieldID}",
                            textAlign = TextAlign.Center
                        )
                    }
                    Box(
                        modifier = Modifier.size(40.dp)
                            .align(Alignment.BottomEnd)
                            .padding(10.dp)
                    )
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
    }
}
