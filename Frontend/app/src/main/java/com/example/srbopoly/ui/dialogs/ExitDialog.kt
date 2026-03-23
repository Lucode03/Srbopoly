package com.example.srbopoly.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ExitDialog(onDismiss:()->Unit,onYes:()->Unit,onNo:()->Unit,text:String?=null)
{
    AlertDialog(
        onDismissRequest = { onDismiss() },

        title = {
            Text("Izlazak iz igre")
        },

        text = {
            Text("Da li ste sigurni da želite da izađete?\n$text")
        },

        confirmButton = {
            Button(
                onClick = {
                    onYes()
                }
            ) {
                Text("Da")
            }
        },

        dismissButton = {
            Button(
                onClick = { onNo() }
            ) {
                Text("Ne")
            }
        }
    )
}