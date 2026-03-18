package com.example.srbopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.srbopoly.R

@Composable
fun GameListScreen(modifier: Modifier = Modifier)
{
    Box(modifier = modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Rankings background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}