package com.example.srbopoly.ui.popups.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.srbopoly.data.fields.JailField

@Composable
fun JailFieldView(
    field: JailField
) {
    Column {
        FieldView(field)
    }
}