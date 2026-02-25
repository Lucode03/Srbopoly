package com.example.srbopoly.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun UserScreen(username: String) {
    Text(text = "Dobrodošao, $username!")
}