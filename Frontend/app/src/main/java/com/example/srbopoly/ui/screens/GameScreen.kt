package com.example.srbopoly.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.srbopoly.viewmodels.GameViewModel

@Composable
fun GameScreen(navController: NavController,viewModel: GameViewModel) {

    val settings by viewModel.gameState

    Column {
        Text("Igra")

        Button(
            onClick = {
                navController.navigate("home"){
                    popUpTo("game") { inclusive = true }
                }
            }
        ) {
            Text("Izađi")
        }
    }
}