package com.example.srbopoly.ui.dialogs.dialogwrappers


import androidx.compose.runtime.Composable
import com.example.srbopoly.data.fields.Field
import com.example.srbopoly.data.fields.PropertyField
import com.example.srbopoly.data.gamedto.PlayerDto
import com.example.srbopoly.data.gamedto.TradeOfferDto
import com.example.srbopoly.ui.dialogs.TradeProposalDialog

@Composable
fun TradeProposalDialogWrapper(
    show: Boolean,
    myId: Int,
    board: List<Field>,
    players: List<PlayerDto>,
    onPropose: (targetId: Int, offer: TradeOfferDto) -> Unit,
    onDismiss: () -> Unit
) {
    if (!show) return

    TradeProposalDialog(
        myId = myId,
        myProperties = board.filterIsInstance<PropertyField>().filter { it.ownerId == myId },
        otherPlayers = players.filter { it.id != myId && !it.isBankrupt },
        allProperties = board.filterIsInstance<PropertyField>(),
        onPropose = onPropose,
        onDismiss = onDismiss
    )
}