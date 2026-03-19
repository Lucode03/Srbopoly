package com.example.srbopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.srbopoly.R
import com.example.srbopoly.classes.UserStats

@Composable
fun GameListScreen(modifier: Modifier = Modifier)
{
    val games = remember { mutableStateListOf<>(
        UserStats("5","Petarx",50),
        UserStats("6","Nikk",120),
        UserStats("7","Illo",110),
        UserStats("8","Jack",10),
        UserStats("9","FullAR",1320),
        UserStats("10","RL",1120)
    ) }

    Box(modifier = modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Main background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

        }
    }
}