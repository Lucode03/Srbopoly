package com.example.srbopoly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.srbopoly.ui.theme.SrbopolyTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.features.auth.LoginScreen
import com.example.srbopoly.features.chat.ChatScreen
import com.example.srbopoly.ui.UserScreen
import com.example.srbopoly.ui.theme.SrbopolyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SrbopolyTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(navController = navController)
                    }
                    composable("user/{userId}/{username}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
                        val username = backStackEntry.arguments?.getString("username") ?: ""
                        UserScreen(userId = userId, username = username,
                            onGameClick = { gameId, uname ->
                                navController.navigate("chat/$gameId/$uname")
                            }
                        )
                    }
                    composable("chat/{gameId}/{username}") { backStackEntry ->
                        val gameId = backStackEntry.arguments?.getString("gameId")?.toInt() ?: 0
                        val username = backStackEntry.arguments?.getString("username") ?: ""

                        ChatScreen(
                            gameId = gameId,
                            username = username
                        )
                    }
                }
            }
        }
    }
}
