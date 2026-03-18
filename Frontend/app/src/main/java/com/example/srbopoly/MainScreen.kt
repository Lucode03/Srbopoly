package com.example.srbopoly

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.srbopoly.ui.NavItem
import com.example.srbopoly.ui.BottomNavigationBar
import com.example.srbopoly.ui.screens.GameListScreen
import com.example.srbopoly.ui.screens.HomeScreen
import com.example.srbopoly.ui.screens.RankingsScreen
import com.example.srbopoly.viewmodels.AuthViewModel

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
    val username by authViewModel.username.collectAsStateWithLifecycle()
    val isLoading by authViewModel.isLoading.collectAsStateWithLifecycle()
    val error by authViewModel.error.collectAsStateWithLifecycle()

    LaunchedEffect(user) {
        if (user==null)
            navController.navigate("login")
    }

    LaunchedEffect(error) {
        error?.let {

        }
    }

    BackHandler {
        if (!mainNavController.popBackStack()) {
            activity?.finish()
        }
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(mainNavController)
        }
    )
    { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = NavItem.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(NavItem.Rankings.route) { RankingsScreen() }
            composable(NavItem.Home.route) {
                HomeScreen(
                    onSignOut = { authViewModel.signout() },
                    username=username)
            }
            composable(NavItem.GameList.route) { GameListScreen() }
        }
    }
}