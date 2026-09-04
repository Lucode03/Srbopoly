package com.example.srbopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.srbopoly.R
import com.example.srbopoly.data.dto.GameSummaryDto
import com.example.srbopoly.ui.NavItem
import com.example.srbopoly.viewmodels.MyGamesViewModel

@Composable
fun GameListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MyGamesViewModel
) {
    val games by viewModel.savedGames.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    val voteState by viewModel.voteState.collectAsStateWithLifecycle()
    val gameResumedEvent by viewModel.gameResumedEvent.collectAsStateWithLifecycle()

    var selectedGameId by remember { mutableStateOf<String?>(null) }
    var hasVotedLocal by remember { mutableStateOf(false) }


    LaunchedEffect(gameResumedEvent) {
        gameResumedEvent?.let { resumedGameId ->
            selectedGameId = null
            hasVotedLocal = false
            viewModel.leaveLobby()
            navController.navigate("game/$resumedGameId") {

                popUpTo(NavItem.GameList.route) { inclusive = false }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Main background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else if (games.isEmpty()) {
            Text(
                text = "Nemate sačuvanih igara.",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp),
                contentPadding = PaddingValues(bottom = 10.dp, top = 30.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(games) { game ->
                    GameSummaryCard(
                        game = game,
                        onClick = {
                            selectedGameId = game.gameId
                            hasVotedLocal = false
                            viewModel.joinLobbyForGame(game.gameId)
                        }
                    )
                }
            }
        }

        if (selectedGameId != null) {
            AlertDialog(
                onDismissRequest = {
                    selectedGameId = null
                    hasVotedLocal = false
                    viewModel.leaveLobby()
                },
                title = { Text("Čekaonica za nastavak") },
                text = {
                    Column {
                        Text("Da bi se igra nastavila, svi igrači moraju da glasaju.")
                        Spacer(modifier = Modifier.height(16.dp))

                        if (voteState != null) {
                            val (total, voted) = voteState!!
                            Text(
                                text = "Glasalo: $voted / $total",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = if (voted == total) Color(0xFF0074CE) else Color.Black
                            )
                        } else {
                            Text("Povezivanje sa serverom...")
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            hasVotedLocal = true
                            viewModel.voteToResume()
                        },
                        enabled = voteState != null && !hasVotedLocal
                    ) {
                        Text(if (hasVotedLocal) "Čekamo ostale..." else "Glasaj za nastavak")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            selectedGameId = null
                            hasVotedLocal = false
                            viewModel.leaveLobby()
                        }
                    ) {
                        Text("Odustani")
                    }
                }
            )
        }
    }
}

@Composable
fun GameSummaryCard(game: GameSummaryDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(3.dp, Color(0xFF0D47A1), RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Igra: ${game.gameId.take(8)}...",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFF0074CE).copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Učesnici:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            game.playerNames.forEach { participant ->
                Text("• $participant", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sačuvano: ${game.savedAtUtc.take(10)}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun Previeww()
{
    //GameListScreen(modifier = Modifier, List<GameSummaryDto>(), {})
}