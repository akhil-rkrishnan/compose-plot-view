package app.android.composepath.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

data class MeasureState(
    val labelCordinates: List<LabelCordinates> = emptyList<LabelCordinates>(),
    val hoverCrossOver: HoverCrossOver = HoverCrossOver(),
    val showIntersection: Boolean = true,
    val mainTextStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
    val subTextStyle: TextStyle = TextStyle(color = Color.LightGray, fontSize = 12.sp),
    val mainDividerStrokeWidth: Float = 5f,
    val subDividerStrokeWidth: Float = 3f,
    val hoverIntersectorStrokeWidth: Float = 2f,
    val step: Int = 100,
    val dashStep: Int = 20,
    val xAxisMainIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    val xAxisSubIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    val yAxisMainIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    val yAxisSubIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
) {
    data class HoverCrossOver(
        val isEnabled: Boolean = true,
        val color: Color = Color.LightGray,
        val dashStep: Int = 20,
        val circleRadius: Float = 8f,
        val crossOverTextColor: Color = color
    )
}