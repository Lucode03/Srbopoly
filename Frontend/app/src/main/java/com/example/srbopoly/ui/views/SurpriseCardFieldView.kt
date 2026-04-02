package com.example.srbopoly.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.srbopoly.data.fields.SurpriseCardField

@Composable
fun SurpriseCardFieldView(
    field: SurpriseCardField
) {
    Column {
        FieldView(field)
    }
}