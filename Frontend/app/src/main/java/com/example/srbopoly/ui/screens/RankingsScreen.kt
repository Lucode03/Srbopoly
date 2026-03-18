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
fun RankingsScreen(modifier: Modifier = Modifier) {
    val rankings = remember { mutableStateListOf<UserStats>() }

    Box(modifier = modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.rankings_background),
            contentDescription = "Rankings background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            itemsIndexed(rankings) { index, user ->
                val backgroundColor = when (index) {
                    0 -> Color(0xFFFFD700)
                    1 -> Color(0xFFC0C0C0)
                    2 -> Color(0xFFCD7F32)
                    else -> Color.White
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth(0.95f)
                        .padding(vertical = 4.dp)
                        .background(backgroundColor, shape = RoundedCornerShape(10.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${index + 1}. ${user.fullName}",
                        fontWeight = FontWeight.Bold,
//                        color = if (user.uid == FirebaseAuth.getInstance().currentUser!!.uid) Color.Blue else Color.Black
                    )
                    Text(
                        "${user.stat}",
                        fontWeight = FontWeight.Bold,
//                        color = if (user.uid == FirebaseAuth.getInstance().currentUser!!.uid) Color.Blue else Color.Black
                    )
                }
            }
        }
    }
}