package com.example.srbopoly.draw_functions

import android.graphics.Rect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas


fun rotateImageBitmap(source: ImageBitmap, degrees: Float): ImageBitmap {
    val bmp = source.asAndroidBitmap()
    val matrix = android.graphics.Matrix().apply { postRotate(degrees) }
    val rotatedBmp = android.graphics.Bitmap.createBitmap(
        bmp, 0, 0, bmp.width, bmp.height, matrix, true
    )
    return rotatedBmp.asImageBitmap()
}

fun DrawScope.drawImageOnCanvas(
    image: ImageBitmap,
    rect: androidx.compose.ui.geometry.Rect
) {
    drawIntoCanvas { canvas ->

        val paint = android.graphics.Paint()

        val src = Rect(
            0,
            0,
            image.width,
            image.height
        )

        val dst = Rect(
            rect.left.toInt(),
            rect.top.toInt(),
            rect.right.toInt(),
            rect.bottom.toInt()
        )

        canvas.nativeCanvas.drawBitmap(
            image.asAndroidBitmap(),
            src,
            dst,
            paint
        )
    }
}