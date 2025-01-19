package app.android.pointwise.data.model

import androidx.compose.ui.graphics.Color
/**
* Data class for Label cordinates
 * @param x x axis
 * @param y y axis
 * @param color Color of the label
 * @param showPointIntersection Enable/Disable point intersection
 * @param dotRadius Radius of the circle
*/
data class LabelCordinates(
    val x: Float,
    val y: Float,
    val color: Color = Color.Black,
    val showPointIntersection: Boolean = true,
    val dotRadius: Float = 10f
)