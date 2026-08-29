package com.example.srbopoly.ui.screens.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.R
import com.example.srbopoly.classes.getDiceImage
import com.example.srbopoly.data.fields.Field
import com.example.srbopoly.data.fields.PropertyField
import com.example.srbopoly.data.fields.getCenterRect
import com.example.srbopoly.data.fields.getFieldOffset
import com.example.srbopoly.data.fields.getFieldSize
import com.example.srbopoly.data.gamedto.toColorName
import com.example.srbopoly.data.getColor
import com.example.srbopoly.data.getFigure
import com.example.srbopoly.draw_functions.drawImageOnCanvas
import com.example.srbopoly.draw_functions.limit
import com.example.srbopoly.draw_functions.rotateImageBitmap
import com.example.srbopoly.data.gamedto.TurnPhase
import com.example.srbopoly.ui.dialogs.FieldInfoDialog
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameBoardView(myId:Int,viewModel: GameViewModel,showPlayerDetails:Boolean=true)
{
    val board_center_image = ImageBitmap.imageResource(R.drawable.board_center)

    val gameState by viewModel.gameState.collectAsState()
    val state = gameState ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Povezivanje sa partijom...")
        }
        return
    }

    val dice1 by viewModel.dice1.collectAsState()
    val dice2 by viewModel.dice2.collectAsState()

    val board = viewModel.board

    val players = state.players
    val playersByField = players.groupBy { it.position }

    val myPlayer = players.find { it.id == myId }
    val currentPlayer = players.getOrNull(state.currentPlayerIndex)
    val isMyTurn = myId == state.currentTurn.playerId

    val pendingPurchaseField by viewModel.pendingPurchaseField.collectAsState()

    var selectedField by remember { mutableStateOf<Field?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }

    val highlighted by viewModel.highlightedFields.collectAsState()

    var selectedPlayerToShowFields by remember { mutableStateOf<Int?>(null) }
    var showPlayerFields by remember { mutableStateOf(false) }


    val isTurnActionsPhase = state.currentTurn.phase == TurnPhase.TURN_ACTIONS

    if (showInfoDialog && selectedField != null) {
        FieldInfoDialog(
            onDismiss = { selectedField = null; showInfoDialog = false },
            field = selectedField!!,
            action = false,
            isMyTurn = isMyTurn,
            playerID = myId,
            onResult = {
                if (it && selectedField is PropertyField) {
                    viewModel.buildHouse((selectedField as PropertyField).GameFieldID)
                }
                selectedField = null
                showInfoDialog = false
            }
        )
    }

    pendingPurchaseField?.let { field ->
        if (field is PropertyField) {
            selectedField = null
            showInfoDialog = false

            FieldInfoDialog(
                onDismiss = { },
                field = field,
                action = true,
                isMyTurn = isMyTurn,
                playerID = myId,
                onResult = { bought -> if (bought) viewModel.buyProperty() else viewModel.declineBuy() }
            )
        }
    }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
    ) {

        Spacer(modifier = Modifier.height((15.dp)))

        Column {
            players.chunked(2).forEach { rowPlayers ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowPlayers.forEach { player ->
                        val isCurrentPlayer = player.id == currentPlayer?.id
                        val cardHeight = if (showPlayerDetails) 120.dp else 40.dp

                        BoxWithConstraints(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            val titleSize = (maxWidth.value * 0.09f).sp
                            val smallSize = (maxWidth.value * 0.08f).sp
                            val iconSize = maxWidth * 0.12f
                            val spacerHeight = (maxWidth.value * 0.02f).dp
                            Card(

                                colors = CardColors(
                                    containerColor = if (isCurrentPlayer) Color(0xff03a5fc) else Color(
                                        0xffd9dcde
                                    ),
                                    contentColor = Color.Black,
                                    disabledContainerColor = Color.Gray,
                                    disabledContentColor = Color.Black
                                ),
                                border = if (isCurrentPlayer) BorderStroke(
                                    width = 2.dp,
                                    Color.Blue
                                ) else BorderStroke(width = 2.dp, Color.Black)
                            )
                            {
                                Column(
                                    modifier = Modifier.padding(8.dp)
                                        .fillMaxSize()
                                ) {
                                    Box(modifier = Modifier.fillMaxWidth())
                                    {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            if (myPlayer?.id == player.id) {
                                                Icon(
                                                    Icons.Default.Person,
                                                    contentDescription = "My player",
                                                    modifier = Modifier.size(iconSize),
                                                    tint = Color.Blue
                                                )
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(
                                                    text = "Ja",
                                                    fontSize = titleSize,
                                                    color = Color.Blue
                                                )
                                            } else {
                                                Text(
                                                    text = player.name,
                                                    fontSize = titleSize,
                                                    color = Color.Black
                                                )
                                            }
                                        }

                                        Image(
                                            painter = painterResource(getFigure(player.color.toColorName())),
                                            contentDescription = "Figurica od ${player.name}",
                                            modifier = Modifier.size(iconSize)
                                                .align(Alignment.CenterEnd)
                                        )
                                    }
                                    if (showPlayerDetails) {


                                        Spacer(modifier = Modifier.height((2.dp)))
                                        HorizontalDivider(
                                            thickness = 1.dp,
                                            color = Color.Black,
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {

                                            Text(
                                                text = "${player.money} "+stringResource(R.string.money),
                                                fontSize = smallSize,
                                                color = Color.Black,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Spacer(modifier = Modifier.height(spacerHeight))
                                            Row {
                                                Text(
                                                    text = board[player.position].Name.limit(14),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    fontSize = smallSize,
                                                    color = Color.Black
                                                )
                                                Text(
                                                    text = " (${player.position})",
                                                    fontSize = smallSize,
                                                    color = Color.Gray
                                                )
                                            }
                                            val buttonSelected =
                                                showPlayerFields && selectedPlayerToShowFields == player.id

                                            Spacer(modifier = Modifier.height(spacerHeight))

                                            Box(
                                                modifier = Modifier
                                                    .width(120.dp)
                                                    .height(30.dp)
                                                    .align(Alignment.CenterHorizontally)
                                                    .clickable {
                                                        if (showPlayerFields && selectedPlayerToShowFields != player.id) {
                                                            selectedPlayerToShowFields = player.id
                                                        } else {
                                                            selectedPlayerToShowFields = if (selectedPlayerToShowFields == null)
                                                                player.id
                                                            else
                                                                null
                                                            showPlayerFields = !showPlayerFields

                                                        }
                                                    }
                                                    .background(
                                                        MaterialTheme.colorScheme.primary,
                                                        shape = RoundedCornerShape(8.dp)
                                                    )
                                            )
                                            {
                                                Text(
                                                    text = if (buttonSelected) "Sakrij posede" else "Prikaži posede",
                                                    fontSize = smallSize,
                                                    color = Color.Black,
                                                    modifier = Modifier.align(Alignment.Center)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (rowPlayers.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            val maxWidth = maxWidth

            Box(modifier = Modifier.size(maxWidth)) {
                board.forEach { field ->

                    val isHighlighted = field.GameFieldID in highlighted

                    val (width, height) = getFieldSize(field.GameFieldID, maxWidth)
                    val hasRowFigures = (field.GameFieldID in 10..19 || field.GameFieldID in 30..39)
                    val (x, y) = getFieldOffset(field.GameFieldID, maxWidth)

                    val playersOnField = playersByField[field.GameFieldID] ?: emptyList()

                    val fieldFillColor by animateColorAsState(
                        if (isHighlighted && currentPlayer != null) getColor(currentPlayer.color.toColorName()) else Color.White
                    )

                    val isOwnedBySelectedPlayer = (field is PropertyField && field.ownerId == selectedPlayerToShowFields)

                    val fieldBorderColor by animateColorAsState(
                        if (showPlayerFields && isOwnedBySelectedPlayer) Color.Blue else Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .offset(x = x, y = y)
                            .size(width, height)
                            .background(fieldFillColor)
                            .clickable {
                                selectedField=field
                                showInfoDialog = true
                            }
                            .border(
                                width = if (showPlayerFields && isOwnedBySelectedPlayer) 3.dp else 1.dp,
                                color = fieldBorderColor
                            )
                    ) {

                        if(!isHighlighted)
                        {
                            val originalImage = ImageBitmap.imageResource(id = Field.getFieldImage(field.FieldType))

                            val rotatedImage = when (field.GameFieldID) {
                                in 1..9 -> rotateImageBitmap(originalImage, 180f)
                                in 11..19 -> rotateImageBitmap(originalImage, -90f)
                                in 31..39 -> rotateImageBitmap(originalImage, 90f)
                                else -> originalImage
                            }
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                bitmap = rotatedImage,
                                contentDescription = "Polje ${field.GameFieldID}",
                                contentScale = ContentScale.FillBounds
                            )
                        }
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
                                        painter = painterResource(id = getFigure(player.color.toColorName())),
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
                                                painter = painterResource(id = getFigure(player.color.toColorName())),
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

                    val centerRect = getCenterRect(size)
                    drawImageOnCanvas(board_center_image, centerRect)

                    drawRect(
                        color = Color.Black,
                        topLeft = centerRect.topLeft,
                        size = centerRect.size,
                        style = Stroke(width = 2.dp.toPx())
                    )
//                    for (field in board) {
//                        val rect = getRect(field.GameFieldID, size)
//                        val isMyField = field.GameFieldID == myPlayer?.Position
//
//                        drawRect(
//                            color = if (isMyField) Color.Red else Color.Black,
//                            topLeft = rect.topLeft,
//                            size = rect.size,
//                            style = Stroke(width = if (isMyField) 4f else 2f)
//                        )
//                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(top=20.dp),
            contentAlignment = Alignment.TopCenter)
        {
            when {
                state.currentTurn.phase == TurnPhase.AWAITING_ROLL -> {
                    Row(
                        modifier = Modifier.clickable(
                            enabled = isMyTurn,
                            onClick = { viewModel.rollDice() }
                        ),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(getDiceImage(dice1)),
                            contentDescription = "Dice 1",
                            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp))
                                .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(12.dp))
                        )
                        Image(
                            painter = painterResource(getDiceImage(dice2)),
                            contentDescription = "Dice 2",
                            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp))
                                .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(12.dp))
                        )
                    }
                }
                state.currentTurn.phase == TurnPhase.TURN_ACTIONS -> {
                    Button(
                        enabled = isMyTurn,
                        onClick = { viewModel.endTurn() },
                        modifier = Modifier.height(36.dp).align(Alignment.Center),
                        elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                    ) {
                        Text(text = "Završi potez", fontSize = 14.sp, color = Color.Black)
                    }
                }
                else -> {
                    // AWAITING_PROPERTY_DECISION, TURN_ENDED, GAME_OVER - popup-i dolaze u 3b/3c
                    Text(
                        text = "Čekanje...",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GffaPreview() {
    val mainNavController = rememberNavController()
    //val viewModel = GameViewModel()
    //GameBoardView(viewModel.players.value[0].id, viewModel)

}