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
    val hasOwner = field.ownerId != null
    Column(modifier = Modifier.fillMaxWidth()) {
        FieldView(field)

        if (!hasOwner) {
            InfoRow("Cena", field.Price.toString(), true)
        }
        else {
            InfoRow("Osnovna renta", field.BaseRent.toString(), true)
        }

        InfoRow("Vlasnik", field.ownerName ?: " - ")
        if (hasOwner) {
            InfoRow("Kuće", minOf(field.houseCount, 4).toString())
            InfoRow("Hotel", if (field.houseCount == 5) "Da" else "Ne")
            if (field.isMortgaged) InfoRow("Hipoteka", "Da")
        }

        Spacer(modifier = Modifier.height(6.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (!hasOwner) "NA PRODAJU" else "PRODATO",
            color = if (!hasOwner) Color(0xFF2E7D32) else Color(0xFFC62828),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}