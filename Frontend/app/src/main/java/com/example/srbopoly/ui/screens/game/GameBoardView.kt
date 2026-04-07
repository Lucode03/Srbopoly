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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import com.example.srbopoly.data.getColor
import com.example.srbopoly.data.getFigure
import com.example.srbopoly.draw_functions.drawImageOnCanvas
import com.example.srbopoly.draw_functions.limit
import com.example.srbopoly.draw_functions.rotateImageBitmap
import com.example.srbopoly.enums.TurnPhase
import com.example.srbopoly.ui.dialogs.FieldInfoDialog
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameBoardView(myId:Int,viewModel: GameViewModel,showPlayerDetails:Boolean=true)
{
    val board_center_image = ImageBitmap.imageResource(R.drawable.board_center)

    val gameState by viewModel.gameState

    val dice1 by viewModel.dice1.collectAsState()
    val dice2 by viewModel.dice2.collectAsState()

    val board = viewModel.board

    val players by viewModel.players
    val playersByField = players.groupBy { it.Position }

    val myPlayer = players.find { it.id == myId }

    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedField by remember { mutableStateOf<Field?>(null) }

    val actionField by viewModel.activeField

    val highlighted by viewModel.highlightedFields.collectAsState()

    var selectedPlayerToShowFields by remember { mutableStateOf<Int?>(null) }
    var showPlayerFields by remember { mutableStateOf(false) }
    if(showInfoDialog)
    {
        FieldInfoDialog(
            onDismiss = {
                selectedField=null
                showInfoDialog=false
            },
            selectedField!!,
            onResult = {
                selectedField=null
                showInfoDialog=false
            },
            playerID=myId
        )
    }
    if(actionField != null)
    {
        val isMyTurn= myId==viewModel.getCurrentPlayer().id
        selectedField=null
        showInfoDialog=false

        FieldInfoDialog(
            onDismiss = {
//                viewModel.applyFieldAction(false)
//                viewModel.clearActiveField()
            },
            actionField!!,
            action=true,
            onResult = { result->
                viewModel.applyFieldAction(result)
                viewModel.clearActiveField()
            },
//            isMyTurn = isMyTurn,
            playerID=myId
        )
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
                        val isCurrentPlayer = player.id == players[gameState.currentPlayer].id
                        val cardHeight=if(showPlayerDetails) 120.dp else 40.dp

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
                                                    text = player.Username,
                                                    fontSize = titleSize,
                                                    color = Color.Black
                                                )
                                            }
                                        }

                                        Image(
                                            painter = painterResource(getFigure(player.Color)),
                                            contentDescription = "Figurica od ${player.Username}",
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
                                                text = "${player.Balance} "+stringResource(R.string.money),
                                                fontSize = smallSize,
                                                color = Color.Black,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Spacer(modifier = Modifier.height(spacerHeight))
                                            Row {
                                                Text(
                                                    text = board[player.Position].Name.limit(14),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    fontSize = smallSize,
                                                    color = Color.Black
                                                )
                                                Text(
                                                    text = " (${player.Position})",
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
                                                            if (selectedPlayerToShowFields == null)
                                                                selectedPlayerToShowFields =
                                                                    player.id
                                                            else
                                                                selectedPlayerToShowFields = null
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
                        if (isHighlighted) getColor(viewModel.getCurrentPlayer().Color) else Color.White
                    )

                    val isOwnedBySelectedPlayer = (field is PropertyField && field.Owner?.id == selectedPlayerToShowFields)

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
            val phase by viewModel.phase

            if(phase==TurnPhase.DICE_ROLL) {
                Row(
                    modifier = Modifier.clickable(
//                    enabled = myPlayer?.id == players[gameState.currentPlayer].id,
                        onClick = {
                            viewModel.movePlayer()
                        }
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
            else
            {
                Button(
                    onClick = {
                        viewModel.nextTurn()
                    },
                    modifier = Modifier.height(36.dp)
                        .align(Alignment.Center),
                    elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                )
                {
                    Text(
                        text = "Završi potez",
                        fontSize = 14.sp,
                        color = Color.Black
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
    val viewModel = GameViewModel()
    GameBoardView(viewModel.players.value[0].id, viewModel)

}