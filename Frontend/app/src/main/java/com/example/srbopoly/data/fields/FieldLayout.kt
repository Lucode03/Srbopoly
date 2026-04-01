package com.example.srbopoly.data.fields

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times


fun getFieldSize(index: Int, maxWidth: Dp): Pair<Dp, Dp> {
    val cornerSize = maxWidth / 7
    val edgeThickness = cornerSize
    val edgeLength = (maxWidth - 2 * cornerSize) / 9

    return when (index) {
        0, 10, 20, 30 -> cornerSize to cornerSize

        in 1..9 -> edgeLength to edgeThickness        // gore
        in 11..19 -> edgeThickness to edgeLength      // desno
        in 21..29 -> edgeLength to edgeThickness      // dole
        in 31..39 -> edgeThickness to edgeLength      // levo

        else -> edgeLength to edgeThickness
    }
}

fun getFieldOffset(index: Int, maxWidth: Dp): Pair<Dp, Dp> {
    val cornerSize = maxWidth / 7
    val edgeThickness = cornerSize
    val edgeLength = (maxWidth - 2 * cornerSize) / 9
    return when (index) {
        0 -> 0.dp to 0.dp

        in 1..9 -> cornerSize + (index - 1) * edgeLength to 0.dp
        10 -> maxWidth - cornerSize to 0.dp

        in 11..19 -> maxWidth - edgeThickness to cornerSize + (index - 11) * edgeLength
        20 -> maxWidth - cornerSize to maxWidth - cornerSize

        in 21..29 -> cornerSize + (29 - index) * edgeLength to maxWidth - edgeThickness
        30 -> 0.dp to maxWidth - cornerSize

        in 31..39 -> 0.dp to cornerSize + (39 - index) * edgeLength

        else -> 0.dp to 0.dp
    }
}

fun getRect(index: Int,size: Size): Rect {
    val cornerSize = size.width / 7
    val edgeThickness = cornerSize
    val edgeLength = (size.width - 2 * cornerSize) / 9

    return when (index) {

        0 -> Rect(0f, 0f, cornerSize, cornerSize)
        10 -> Rect(size.width- cornerSize, 0f, size.width, cornerSize)
        20 -> Rect(size.width - cornerSize, size.height - cornerSize, size.width, size.height)
        30 -> Rect(0f, size.height - cornerSize, cornerSize, size.height)

        in 1..9 -> {
            val x = cornerSize + (index - 1) * edgeLength
            Rect(x, 0f, x + edgeLength, edgeThickness)
        }

        in 11..19 -> {
            val y = cornerSize + (index - 11) * edgeLength
            Rect(size.width - edgeThickness, y, size.width, y + edgeLength)
        }

        in 21..29 -> {
            val x = cornerSize + (29 - index) * edgeLength
            Rect(x, size.height - edgeThickness, x + edgeLength, size.height)
        }

        in 31..39 -> {
            val y = cornerSize + (39 - index) * edgeLength
            Rect(0f, y, edgeThickness, y + edgeLength)
        }

        else -> Rect.Zero
    }
}

fun getCenterRect(size:Size):Rect{

    val cornerSize = size.width / 7
    val edgeThickness = cornerSize

    val centerStart = edgeThickness
    val centerSize = size.width - 2 * edgeThickness

    return Rect(centerStart, centerStart, centerStart+centerSize, centerStart+centerSize)
}