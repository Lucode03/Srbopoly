package com.example.srbopoly

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import com.example.srbopoly.ui.theme.SrbopolyTheme
import com.example.srbopoly.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: AuthViewModel by viewModels()
        setContent {
            SrbopolyTheme {
                AuthNavigation(modifier = Modifier,viewModel = viewModel)
            }
        }
    }
}
