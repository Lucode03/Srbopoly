package com.example.srbopoly.ui.popups.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.srbopoly.data.fields.BonusField

@Composable
fun BonusFieldView(
    field: BonusField
) {
    Column {
        FieldView(field)

        InfoRow("Bonus",field.Bonus.toString(),true)
    }
}
