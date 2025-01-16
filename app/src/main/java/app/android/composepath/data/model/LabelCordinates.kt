package app.android.composepath.data.model

import androidx.compose.ui.graphics.Color

data class LabelCordinates(
    val x : Float,
    val y: Float,
    val color: Color = Color.Black,
    val showPointIntersection: Boolean = true,
    val dotRadius: Float = 10f
)