package com.example.srbopoly.ui.dialogs.dialogwrappers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.example.srbopoly.ui.animations.ActionResultAnimation
import com.example.srbopoly.ui.animations.DiceResultAnimation

@Composable
fun DiceResultAnimationWrapper(
    diceResult: Int?
) {
    if (diceResult != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .zIndex(10f)
            ,
            contentAlignment = Alignment.Center
        ) {
            DiceResultAnimation(diceResult)
        }
    }
}

@Composable
fun ActionResultAnimationWrapper(
    actionResult: String?
) {
    if (actionResult != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .zIndex(10f)
            ,
            contentAlignment = Alignment.Center
        ) {
            ActionResultAnimation(actionResult)
        }
    }
}