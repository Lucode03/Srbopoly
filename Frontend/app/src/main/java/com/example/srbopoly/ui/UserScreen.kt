package com.example.srbopoly.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.TextField
import com.example.srbopoly.data.Game

@Composable
fun UserScreen(
    userId: Int,
    username: String,
    onGameClick: (gameId: Int, username: String) -> Unit,
    viewModel: UserScreenViewModel = hiltViewModel()
) {
    val games by viewModel.games.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val joinIdInput by viewModel.joinGameIdInput.collectAsState()

    LaunchedEffect(key1 = userId) {
        viewModel.fetchUserGames(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Dobrodošao, $username!")
        Button(
            onClick = { viewModel.createNewGame(userId) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            enabled = !isLoading
        ) {
            Text(text = "Započni novu igru")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = joinIdInput,
                onValueChange = { viewModel.onJoinGameIdChange(it) },
                label = { Text("Game ID") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                enabled = !isLoading
            )

            Button(
                onClick = { viewModel.joinExistingGame(userId) },
                enabled = !isLoading && joinIdInput.isNotBlank()
            ) {
                Text("Pridruži se")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (error != null) {
            Text(text = "Greška: $error")
        } else if (games.isEmpty()) {
            Text(text = "Trenutno nemaš započetih igara.")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(games) { game ->
                    GameItemCard(game = game) {
                        onGameClick(game.id, username)
                    }
                }
            }
        }
    }
}

@Composable
fun GameItemCard(
    game: Game,
    onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Igra ID: ${game.id}")
            Text(text = "Status: ${game.status}")
            Text(text = "Potez: ${game.currentTurn} / ${game.maxTurns}")
        }
    }
}
