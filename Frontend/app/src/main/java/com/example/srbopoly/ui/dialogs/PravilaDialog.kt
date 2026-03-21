package com.example.srbopoly.ui.dialogs

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.srbopoly.ui.screens.HomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PravilaDialog(onDismiss: () ->Unit)
{
    val rules = listOf(
        "Opšte informacije" to "U jednoj partiji učestvuju 2 do 4 igrača. Cilj igre je da ostanete poslednji igrač sa novcem.",
        "Pre početka partije" to "Igrači mogu podesiti maksimalan broj poteza (minimum 50 po igraču). Svaki igrač bira jedinstvenu boju figurice. Svi igrači bacaju dve kockice da odrede redosled igranja. Najveći zbir igra prvi.",
        "Potez igrača" to "Na početku poteza igrač baca dve kockice i pomera se za njihov zbir. Igrač ima 20 sekundi za bacanje kockica i 120 sekundi za završetak poteza. Potez se može završiti ranije.",
        "Nekretnine" to "Kada igrač stane na određeno polje nekretnina, može ga kupiti za određenu sumu, ako ga nije pre njega kupio drugi igrač. Ako jeste onda praća određenu sumu za stajanje na to polje. Ako igrač poseduje sva polja iste grupe, može graditi kuće i hotele na tim poljima.",
        "Tabla" to "Tabla se sastoji od: 22 kupovna polja (4 premijum, 18 standardnih), 5 pola sa izvlačenjem kartica, 4 ugla (Start, Zatvor, Idi u zatvor, Parking), 2 polja sa porezom, 5 nacionalnih parkova, 1 vodovod i 1 elektrodistribucija.",
        "Pobednik" to "Pobednik je igrač koji poslednji ostane sa novcem.",
        "Na kraju" to "Srećno!"
    )


    val pagerState = rememberPagerState(pageCount = { rules.size })
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black.copy(alpha = 0.7f))
                .padding(20.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(24.dp)
                            .background(Color.Black.copy(0.5f), CircleShape)
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.height(250.dp)
                ) { page ->

                    val (title, description) = rules[page]

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = description,
                            fontSize = 16.sp,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(rules.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (pagerState.currentPage == index) 10.dp else 6.dp)
                                .background(
                                    if (pagerState.currentPage == index) Color.White else Color.Gray,
                                    CircleShape
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (pagerState.currentPage < rules.lastIndex) {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    page=pagerState.currentPage + 1,
                                    animationSpec = tween(
                                        durationMillis = 400,
                                        easing = FastOutSlowInEasing
                                    )
                                )
                            }
                        } else {
                            onDismiss()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (pagerState.currentPage == rules.lastIndex) "Završi"
                        else "Dalje"
                    )
                }
            }
        }
    }
}