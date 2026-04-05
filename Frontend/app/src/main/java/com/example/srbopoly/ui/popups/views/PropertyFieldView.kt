package com.example.srbopoly.ui.popups.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.srbopoly.data.fields.PropertyField

@Composable
fun PropertyFieldView(
    field: PropertyField
) {
    val hasOwner = field.Owner != null
    Column(modifier = Modifier.fillMaxWidth()) {
        FieldView(field)

        if (!hasOwner) {
            InfoRow("Cena", field.Price.toString(), true)
        }
        else {
            InfoRow("Izdavanje", field.CalculateRent().toString(), true)
        }

        if (hasOwner) {
            InfoRow("Vlasnik", field.Owner!!.Username)

            InfoRow("Kuće", field.Houses.toString())

            InfoRow("Hoteli", field.Hotels.toString())
        }
        else
        {
            InfoRow("Vlasnik"," - ")
        }

        Spacer(modifier = Modifier.height(6.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (field.Owner == null) "NA PRODAJU" else "PRODATO",
            color = if (field.Owner == null) Color(0xFF2E7D32) else Color(0xFFC62828),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}