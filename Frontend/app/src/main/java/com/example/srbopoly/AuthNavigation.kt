package com.example.srbopoly

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.ui.screens.LoginScreen
import com.example.srbopoly.ui.screens.SignUpScreen
import com.example.srbopoly.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuthNavigation(modifier: Modifier = Modifier, viewModel: AuthViewModel)
{
    val navController = rememberNavController()
    val user by viewModel.user.collectAsStateWithLifecycle()
    val isLoaded by viewModel.isSessionLoaded.collectAsStateWithLifecycle()

    if (!isLoaded) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    NavHost(
        navController=navController,
        startDestination = if (user != null) "main" else "login",
        builder ={
        composable("login") {
            LoginScreen(modifier,navController,viewModel)
        }
        composable("signup") {
            SignUpScreen(modifier,navController,viewModel)
        }
        composable("main") {
            MainScreen(modifier,navController,viewModel)
        }
//        composable("user/{userId}/{username}") { backStackEntry ->
//            val userId = backStackEntry.arguments?.getString("userId")?.toInt() ?: 0
//            val username = backStackEntry.arguments?.getString("username") ?: ""
//            UserScreen(userId = userId, username = username,
//                onGameClick = { gameId, uname ->
//                    navController.navigate("chat/$gameId/$uname")
//                }
//            )
//        }
//        composable("chat/{gameId}/{username}") { backStackEntry ->
//            val gameId = backStackEntry.arguments?.getString("gameId")?.toInt() ?: 0
//            val username = backStackEntry.arguments?.getString("username") ?: ""
//
//            ChatScreen(
//                gameId = gameId,
//                username = username
//            )
//        }

    })
}