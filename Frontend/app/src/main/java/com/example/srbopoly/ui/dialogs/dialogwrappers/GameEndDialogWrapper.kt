package com.example.srbopoly.ui.dialogs.dialogwrappers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.srbopoly.data.gamedto.GameEndReason
import com.example.srbopoly.data.gamedto.GameEndedEvent
import com.example.srbopoly.data.gamedto.GameStateSnapshotDto

@Composable
fun GameEndDialogWrapper(
    gameEndedInfo: GameEndedEvent?,
    gameState: GameStateSnapshotDto?,
    onClick: () -> Unit = {}
) {
    gameEndedInfo?.let { ended ->
        val winnerName = gameState?.players?.firstOrNull { it.id == ended.winnerPlayerId }?.name ?: "Nepoznat igrač"
        val reasonText = when (ended.reason) {
            GameEndReason.LAST_PLAYER_STANDING -> "Svi ostali igrači su bankrotirali"
            GameEndReason.TURN_LIMIT_REACHED -> "Dostignut je limit rundi"
        }

        Dialog(onDismissRequest = { }) {
            Card(shape = RoundedCornerShape(20.dp), modifier = Modifier.padding(16.dp)) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Igra je gotova!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Pobednik: $winnerName", fontSize = 16.sp)
                    Text(reasonText, fontSize = 14.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onClick) {
                        Text("Nazad na početnu")
                    }
                }
            }
        }
    }
}