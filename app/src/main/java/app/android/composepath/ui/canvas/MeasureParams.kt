package app.android.composepath.ui.canvas

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MeasureState(
    val labelCordinates: List<LabelCordinates> = emptyList<LabelCordinates>(),
    val showIntersection: Boolean = true,
    val mainTextStyle: TextStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
    val subTextStyle: TextStyle = TextStyle(color = Color.LightGray, fontSize = 12.sp),
    val mainDividerStrokeWidth: Float = 5f,
    val subDividerStrokeWidth: Float = 3f,
    val step: Int = 100,
    val xAxisMainIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    val xAxisSubIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    val yAxisMainIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
    val yAxisSubIntersectionLineColor: Color = Color.Black.copy(alpha = 0.1f),
)