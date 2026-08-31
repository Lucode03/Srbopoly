package com.example.srbopoly.ui.dialogs.dialogwrappers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srbopoly.data.fields.Field
import com.example.srbopoly.data.gamedto.PendingTradeDto
import com.example.srbopoly.data.gamedto.PlayerDto
import com.example.srbopoly.ui.dialogs.IncomingTradeDialog

@Composable
fun IncomingTradeDialogWrapper(
    pendingTrade: PendingTradeDto?,
    myId: Int,
    players: List<PlayerDto>,
    board: List<Field>,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    pendingTrade?.let { trade ->
        when {
            trade.recipientId == myId -> {
                IncomingTradeDialog(
                    trade = trade,
                    proposerName = players.firstOrNull { it.id == trade.proposerId }?.name ?: "Igrač",
                    fieldName = { id -> board.getOrNull(id)?.Name ?: "polje" },
                    onAccept = onAccept,
                    onReject = onReject
                )
            }
            trade.proposerId == myId -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3CD))) {
                        Text(
                            "Čekaš odgovor od ${players.firstOrNull { it.id == trade.recipientId }?.name ?: "igrača"}...",
                            modifier = Modifier.padding(12.dp),
                            fontSize = 13.sp
                        )
                    }
                }
            }
            else -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E8E8))) {
                        Text(
                            "${players.firstOrNull { it.id == trade.proposerId }?.name} pregovara sa ${players.firstOrNull { it.id == trade.recipientId }?.name}",
                            modifier = Modifier.padding(12.dp),
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}