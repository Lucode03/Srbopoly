package com.example.srbopoly.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srbopoly.R
import com.example.srbopoly.data.User
import com.example.srbopoly.ui.dialogs.PravilaDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, onSignOut:()->Unit,
               user: User, onStartGame:()->Unit)
{
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = "Main background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        TopAppBar(
            title = {
                Column()
                {
                    Text(
                        text = user.username,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            },
            actions = {
                Icon(
                    Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Odjavite se",
                    modifier=Modifier.size(40.dp).clickable { onSignOut() },
                    Color.Black
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFD9D9D9),
                titleContentColor = Color.White
            ),
            expandedHeight= 40.dp
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "\uD83E\uDE99 broj_poena",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.game_logo),
                contentDescription = "Game",
                modifier = Modifier
                    .size(300.dp)
                    .shadow(
                        elevation = 40.dp,
                        shape = RoundedCornerShape(20.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .border(3.dp,Color(0xFF001FE1),RoundedCornerShape(20.dp))
                    .clickable {
                        showDialog = true
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))

            val elevation by animateDpAsState(
                targetValue = 10.dp
            )

            Button(
                onClick = { onStartGame() },
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.buttonElevation(elevation),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF001FE1),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp)
            ) {
                Text(
                    text = "Nova igra",
                    fontSize = 20.sp
                )
            }
        }
        if (showDialog) {
            PravilaDialog { showDialog = false }
        }
    }
}

@Preview
@Composable
fun HomeScreenPrev()
{
    HomeScreen(modifier = Modifier, onSignOut={},
    user=User(1,"Username"), onStartGame={})
}