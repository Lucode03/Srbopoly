package com.example.srbopoly

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.ui.NavItem
import com.example.srbopoly.ui.BottomNavigationBar
import com.example.srbopoly.ui.screens.GameListScreen
import com.example.srbopoly.ui.screens.GameScreen
import com.example.srbopoly.ui.screens.HomeScreen
import com.example.srbopoly.ui.screens.RankingsScreen
import com.example.srbopoly.ui.screens.SettingsScreen
import com.example.srbopoly.viewmodels.AuthViewModel
import com.example.srbopoly.viewmodels.GameViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(modifier: Modifier = Modifier,
               navController: NavController,
               authViewModel: AuthViewModel
)
{
    val mainNavController = rememberNavController()
    val context = LocalContext.current
    val activity = context as? Activity

    val user by authViewModel.user.collectAsStateWithLifecycle()
    val error by authViewModel.error.collectAsStateWithLifecycle()


    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(user) {
        if (user==null)
            navController.navigate("login")
    }

    LaunchedEffect(error) {
        error?.let {
            val message = when {
                it.contains("401") -> "Pogrešni podaci za prijavu"
                it.contains("network") -> "Nema internet veze"
                else -> it
            }

            snackbarHostState.showSnackbar(message)
            authViewModel.clearError()
        }
    }

    BackHandler {
        if (!mainNavController.popBackStack()) {
            activity?.finish()
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomNavigationBar(mainNavController)
        }
    )
    { innerPadding ->
        val gameViewModel: GameViewModel = hiltViewModel()
        NavHost(
            navController = mainNavController,
            startDestination = NavItem.Home.route,
            modifier = modifier.padding( innerPadding)
        ) {
            composable(NavItem.Rankings.route) { user?.let { it1 -> RankingsScreen(user= it1) } }
            composable(NavItem.Home.route) {
                user?.let { it1 ->
                    HomeScreen(
                        onLogOut = { authViewModel.signout() },
                        user = it1,
                        onStartGame = { code ->
                            mainNavController.navigate("settings/$code")
                        },
                        onJoinGame = { code ->
                            mainNavController.navigate("settings/$code")
                        }
                    )
                }
            }
            composable(NavItem.GameList.route) { user?.let { it1 -> GameListScreen(user = it1) } }
            composable("settings/{gameCode}") { backStackEntry ->
                val gameCode = backStackEntry.arguments?.getString("gameCode") ?: ""
                user?.let { it
                    SettingsScreen(mainNavController, myId = it.id, gameCode = gameCode)
                }
            }

            composable("game") {
                GameScreen(mainNavController,gameViewModel)
            }
        }
    }
}