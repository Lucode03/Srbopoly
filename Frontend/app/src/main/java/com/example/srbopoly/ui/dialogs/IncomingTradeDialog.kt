package com.example.srbopoly.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.srbopoly.data.gamedto.PendingTradeDto

@Composable
fun IncomingTradeDialog(
    trade: PendingTradeDto,
    proposerName: String,
    fieldName: (Int) -> String,
    onAccept: () -> Unit,
    onReject: () -> Unit
) {
    Dialog(onDismissRequest = { }) {
        Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(12.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Ponuda za razmenu od $proposerName", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Nudi ti:")
                trade.offeredPropertyIds.forEach { Text("• ${fieldName(it)}") }
                if (trade.offeredMoney > 0) Text("• ${trade.offeredMoney} novca")

                Spacer(modifier = Modifier.height(8.dp))
                Text("Traži od tebe:")
                trade.requestedPropertyIds.forEach { Text("• ${fieldName(it)}") }
                if (trade.requestedMoney > 0)
                    Text("• ${trade.requestedMoney} novca")

                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = onAccept) { Text("Prihvati") }
                    Button(onClick = onReject) { Text("Odbij") }
                }
            }
        }
    }
}