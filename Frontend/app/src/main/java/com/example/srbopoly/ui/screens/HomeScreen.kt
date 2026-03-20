package com.example.srbopoly.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srbopoly.R
import com.example.srbopoly.ui.dialogs.PravilaDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, onSignOut:()->Unit,
               username:String, onStartGame:()->Unit)
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
                Text(
                    text = username,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            actions = {
                TextButton(onClick = onSignOut) {
                    Icon(
                        Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Odjavite se",
                        modifier=Modifier.size(40.dp),
                        Color.Black
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFD9D9D9),
                titleContentColor = Color.White
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

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

            Button(
                onClick = { onStartGame() },
                shape = RoundedCornerShape(20.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp
                ),
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