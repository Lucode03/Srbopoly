package com.example.srbopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srbopoly.R
import com.example.srbopoly.data.User
import kotlinx.coroutines.launch

@Composable
fun RankingsScreen(modifier: Modifier = Modifier,user:User) {
    val rankings = remember { mutableStateListOf(
        User(5,"Petarx",50),
        User(6,"Nikk",120),
        User(7,"Illo",110),
        User(8,"Jack",10),
        User(15,"FullAR",1320),
        User(10,"RL",1120),
        User(5,"Petarx",50),
        User(6,"Nikk",120),
        User(7,"Illo",110),
        User(8,"Jack",10),
        User(15,"FullAR",1320),
        User(10,"RL",1120),
        User(5,"Petarx",50),
        User(6,"Nikk",120),
        User(7,"Illo",110),
        User(8,"Jack",10),
        User(15,"FullAR",1320),
        User(10,"RL",1120),
        User(5,"Petarx",50),
        User(6,"Nikk",120),
        User(7,"Illo",110),
        User(8,"Jack",10),
        User(15,"FullAR",1320),
        User(10,"RL",1120)
    ) }+user

    val sortedRankings = rankings.sortedByDescending { it.points }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    BoxWithConstraints()
    {
        val maxWidth=this.maxWidth
        Image(
            painter = painterResource(id = R.drawable.rankings_background),
            contentDescription = "Rankings background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val myIndex = sortedRankings.indexOfFirst { it.id == user.id }
            val myBackgroundColor = when (myIndex) {
                0 -> Color(0xFFFFD700)
                1 -> Color(0xFFC0C0C0)
                2 -> Color(0xFFCD7F32)
                else -> Color(0xFF2196F3)
            }
            Box(
                modifier = Modifier
                    .width(maxWidth)
                    .background(Color(0xFFE3E3E3), RoundedCornerShape(16.dp))
                    .border(2.dp,Color.Black, RoundedCornerShape(16.dp))
                    .clickable {
                        coroutineScope.launch {
                            listState.animateScrollToItem(
                                index = myIndex,
                                scrollOffset = -100
                            )
                        }
                    }
                    .padding(10.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = user.username,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "#${myIndex+1}",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = myBackgroundColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "\uD83C\uDFC5 ${user.points}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                state = listState,
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                itemsIndexed(sortedRankings) { index, userRanked ->
                    val backgroundColor = when (index) {
                        0 -> Color(0xFFFFD700)
                        1 -> Color(0xFFC0C0C0)
                        2 -> Color(0xFFCD7F32)
                        else -> Color.White
                    }
                    Row(
                        modifier = modifier
                            .width(maxWidth)
                            .padding(vertical = 4.dp)
                            .background(backgroundColor, shape = RoundedCornerShape(10.dp))
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "${index + 1}. ${userRanked.username}",
                            fontWeight = FontWeight.Bold,
                            color = if (userRanked.id == user.id) Color.Blue else Color.Black
                        )
                        Text(
                            "\uD83C\uDFC5 ${userRanked.points}",
                            fontWeight = FontWeight.Bold,
                            color = if (userRanked.id == user.id) Color.Blue else Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RankingsScreenPrevv()
{
    RankingsScreen(modifier=Modifier ,user=User(1,"usernam",1150))
}