package com.example.srbopoly.ui.popups.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.srbopoly.data.fields.NationalParkField

@Composable
fun NationalParkFieldView(
    field: NationalParkField
) {
    Column {
        FieldView(field)
    }
}