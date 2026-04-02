package com.example.srbopoly.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.srbopoly.data.fields.PaymentField

@Composable
fun PaymentFieldView(
    field: PaymentField
) {
    Column {
        FieldView(field)

        InfoRow("Taksa",field.Price.toString(),true)
    }
}