package com.example.srbopoly.ui.popups.actions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.srbopoly.data.fields.NationalParkField

@Composable
fun NationalParkFieldAction(
    field: NationalParkField,
    action: Boolean = false,
    onResult: (Boolean) -> Unit,
    modifier: Modifier,
    isMyTurn:Boolean
){
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (action)
        {
            Button(onClick = {
                onResult(true)
            },
                enabled = isMyTurn
            ) {
                Text("Zatvori")
            }
        }
        else
        {
            Button(onClick = {
                onResult(false)
            },
                enabled = isMyTurn
            ) {
                Text("Zatvori")
            }
        }
    }
}