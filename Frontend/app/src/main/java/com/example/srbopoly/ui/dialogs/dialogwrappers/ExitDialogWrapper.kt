package com.example.srbopoly.ui.dialogs.dialogwrappers

import androidx.compose.runtime.Composable
import com.example.srbopoly.ui.dialogs.ExitDialog

@Composable
fun ExitDialogWrapper(
    showQuitDialog: Boolean,
    onDismiss: () -> Unit = {},
    onYes: () -> Unit = {},
    onNo: () -> Unit = {}
) {
    if (showQuitDialog) {
        ExitDialog(
            onDismiss = onDismiss,
            onYes = onYes,
            onNo = onNo,
            text = "Igra može biti nastavljena samo ukoliko svi ostali igrači ponovo uđu!"
        )
    }
}