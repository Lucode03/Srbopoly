package com.example.srbopoly.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srbopoly.data.fields.Field

@Composable
fun FieldView(
    field: Field
) {
    val text=Field.getType(field.FieldType)
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = field.Name+ if(text!="") " - $text" else "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(6.dp))

        HorizontalDivider(color = Color.LightGray)

        Spacer(modifier = Modifier.height(10.dp))

        InfoRow("Pozicija", field.GameFieldID.toString())

    }
}

@Composable
fun InfoRow(label: String, value: String,cash:Boolean=false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Text(
            text = value + if (cash)"   \uD83D\uDCB5" else "",
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

    Spacer(modifier = Modifier.height(6.dp))
}