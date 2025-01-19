package app.android.pointwise.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

/**
 * Data class for point state. Set the params for customized view
 * @param labelCordinates List of coordinates provided by the user to be plotted on the screen @see[LabelCordinates]
 * @param hoverCrossOver State of dragging crossover intersection @See[HoverCrossOver]
 * @param showIntersection true - Show intersection from top to bottom and left to right
 * @param mainTextStyle Style for the main marker text
 * @param subTextStyle Style for the sub market text
 * @param mainDividerStrokeWidth Stroke width of main marker
 * @param subDividerStrokeWidth Stroke width of sub marker
 * @param step The step count for breaking the width and height, higher the number less division
 * @param dashStep The dash step for the dashed intersection line
 * @param xAxisMainIntersectionLineColor Color for the x axis main intersection line
 * @param xAxisSubIntersectionLineColor Color for the x axis sub intersection line
 * @param yAxisMainIntersectionLineColor Color for the y axis main intersection line
 * @param yAxisSubIntersectionLineColor Color for the y axis sub intersection line
 */
data class PointState(
    val labelCordinates: List<LabelCordinates> = emptyList<LabelCordinates>(),
    val hoverCrossOver: HoverCrossOver = HoverCrossOver(),
    val showIntersection: Boolean = true,
    val mainTextStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
    val subTextStyle: TextStyle = TextStyle(color = Color.LightGray, fontSize = 12.sp),
    val mainDividerStrokeWidth: Float = 5f,
    val subDividerStrokeWidth: Float = 3f,
    val step: Int = 100,
    val dashStep: Int = 20,
    val xAxisMainIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    val xAxisSubIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    val yAxisMainIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    val yAxisSubIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
) {
    /**
     * Data class for hover cross over
     * @param isEnabled Enable/disable the hover cross over
     * @param color Color of the cross over
     * @param dashStep The dash step for the intersection line
     * @param circleRadius The radius of pointer circle
     * @param crossOverTextColor Color for the pointer text
     */
    data class HoverCrossOver(
        val isEnabled: Boolean = true,
        val color: Color = Color.LightGray,
        val dashStep: Int = 20,
        val circleRadius: Float = 8f,
        val crossOverTextColor: Color = color
    )
}