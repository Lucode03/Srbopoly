package com.example.srbopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srbopoly.R
import com.example.srbopoly.classes.GameStats
import com.example.srbopoly.data.User

@Composable
fun GameListScreen(modifier: Modifier = Modifier,user: User)
{
    val games = remember { mutableStateListOf(
        GameStats("1",50,24,"Ivan", listOf("Ivan","Petar","Aca")),
        GameStats("2",120,53,"Petar",listOf("Zeika","ivv","Petar","Aca")),
        GameStats("3",40,12,"Nikola",listOf("Iggor","Nikola","Nikk")),
        GameStats("4",80,3,"Luka321",listOf("Luka321","Zemni","Aca")),
    ) }

    Box(
        modifier = modifier.fillMaxSize()
       )
    {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Main background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        LazyColumn(
            modifier = modifier.fillMaxSize()
                .padding(
                    horizontal = 26.dp
                ),
            contentPadding = PaddingValues(
                bottom = 10.dp,
                top = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            items(games) { game ->
                GameCard(game = game,
                    onClick = {

                })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun GameCard(game: GameStats, onClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = Color(0xFF0D47A1),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "Igra ${game.uid}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )

                Text(
                    text = "${game.currentTurn} / ${game.maxTurns}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(color = Color(0xFF0074CE).copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Na potezu: ${game.currentPlayer}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Igrači:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            game.players.forEach {
                Text("• $it",fontSize = 16.sp)
            }
        }
    }
}


@Preview
@Composable
fun Previeww()
{
    GameListScreen(modifier = Modifier, user = User(1,"username"))
}