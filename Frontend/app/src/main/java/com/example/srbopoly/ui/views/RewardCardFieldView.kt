package com.example.srbopoly.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.srbopoly.data.fields.RewardCardField

@Composable
fun RewardCardFieldView(
    field: RewardCardField
) {
    Column {
        FieldView(field)
    }
}