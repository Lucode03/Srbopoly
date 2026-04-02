package com.example.srbopoly.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.srbopoly.data.fields.MovementField

@Composable
fun MovementFieldView(
    field: MovementField
) {
    Column {
        FieldView(field)
    }
}