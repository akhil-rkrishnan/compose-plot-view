package app.android.composepath.ui.canvas

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import app.android.composepath.data.model.MeasureState
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import app.android.composepath.utils.isActual


private const val TAG = "MeasureView"

@Composable
fun MeasureView(
    modifier: Modifier = Modifier,
    measureState: MeasureState = MeasureState(),
    view: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val width = configuration.screenWidthDp * density
    val height = configuration.screenHeightDp * density

    var dragOffset by remember {
        mutableStateOf(Offset(0f,0f))
    }

    Log.d(TAG, "Screen width and height => $width, $height")
    val textMeasurer = rememberTextMeasurer()
    Box(modifier = modifier.pointerInput(Unit, Unit, {
        detectDragGestures(onDragStart = { offset ->
            dragOffset = offset
            Log.d(TAG, "onDragStart: $dragOffset")
        }, onDrag = { pic, offset ->
            pic.consume()
            dragOffset = offset
            Log.d(TAG, "onDrag: $dragOffset")
        }, onDragEnd = {

        })
    })) {
        view.invoke()
        Canvas(modifier = Modifier) {
            if (dragOffset.isActual()) {
                drawCircle(color = Color.Black, radius = 8f, center = dragOffset)
            }
            //x axis
            for (axisValue in measureState.step until width.toInt() step measureState.step) {
                val mainSplit = (axisValue.mod(measureState.step * 2)) == 0
                if (mainSplit) {
                    DrawMarkers(
                        color = Color.DarkGray,
                        start = Offset(axisValue.toFloat(), 0f),
                        end = Offset(axisValue.toFloat(), 25f),
                        strokeWidth = 5f
                    )
                    if (measureState.showIntersection) {
                        DrawIntersection(
                            color = Color.Black.copy(0.1f),
                            start = Offset(axisValue.toFloat(), 0f),
                            end = Offset(axisValue.toFloat(), height),
                            strokeWidth = 3f
                        )
                    }
                    DrawText(
                        value = "${axisValue}x",
                        textMeasurer = textMeasurer,
                        color = Color.Black,
                        offset = Offset(axisValue.toFloat() - 23, 30f)
                    )
                } else {
                    DrawMarkers(
                        color = Color.LightGray,
                        start = Offset(axisValue.toFloat(), 0f),
                        end = Offset(axisValue.toFloat(), 25f),
                        strokeWidth = 3f
                    )
                    if (measureState.showIntersection) {
                        DrawIntersection(
                            color = Color.Black.copy(0.1f),
                            start = Offset(axisValue.toFloat(), 0f),
                            end = Offset(axisValue.toFloat(), height),
                            strokeWidth = 3f
                        )
                    }
                    DrawText(
                        value = "${axisValue}x",
                        textMeasurer = textMeasurer,
                        offset = Offset(axisValue.toFloat() - 23, 30f)
                    )
                }
            }
            // y axis
            for (axisValue in measureState.step until height.toInt() step measureState.step) {
                val mainSplit = (axisValue.mod(measureState.step * 2)) == 0
                if (mainSplit) {
                    DrawMarkers(
                        color = Color.DarkGray,
                        start = Offset(0f, axisValue.toFloat()),
                        end = Offset(25f, axisValue.toFloat()),
                        strokeWidth = 5f
                    )
                    if (measureState.showIntersection) {
                        DrawIntersection(
                            color = Color.Black.copy(alpha = 0.1f),
                            start = Offset(0f, axisValue.toFloat()),
                            end = Offset(width, axisValue.toFloat()),
                            strokeWidth = 1f
                        )
                    }
                    DrawText(
                        value = "${axisValue}y",
                        textMeasurer = textMeasurer,
                        color = Color.Black,
                        offset = Offset(30f, axisValue.toFloat() - 10)
                    )
                } else {
                    DrawMarkers(
                        color = Color.LightGray,
                        start = Offset(0f, axisValue.toFloat()),
                        end = Offset(25f, axisValue.toFloat()),
                        strokeWidth = 3f
                    )
                    if (measureState.showIntersection) {
                        DrawIntersection(
                            color = Color.Black.copy(alpha = 0.1f),
                            start = Offset(0f, axisValue.toFloat()),
                            end = Offset(width, axisValue.toFloat()),
                            strokeWidth = 1f
                        )
                    }
                    DrawText(
                        value = "${axisValue}y",
                        textMeasurer = textMeasurer,
                        offset = Offset(30f, axisValue.toFloat() - 10)
                    )
                }
            }
            measureState.labelCordinates.forEach { cordinate ->
                if (cordinate.showPointIntersection) {
                    val dashStep = 20
                    for (y in 0 until cordinate.y.toInt() step dashStep) {
                        DrawIntersection(
                            cordinate.color,
                            start = Offset(cordinate.x, y.toFloat()),
                            end = Offset(cordinate.x, (y + (dashStep / 2)).toFloat()),
                            strokeWidth = 3f
                        )
                    }
                    for (x in 0 until cordinate.x.toInt() step dashStep) {
                        DrawIntersection(
                            cordinate.color,
                            start = Offset(x.toFloat(), cordinate.y),
                            end = Offset((x + (dashStep / 2)).toFloat(), cordinate.y),
                            strokeWidth = 3f
                        )
                    }
                }
                if (cordinate.x.toInt().mod(measureState.step) != 0) {
                    DrawMarkers(
                        color = cordinate.color,
                        start = Offset(cordinate.x, 0f),
                        end = Offset(cordinate.x, 10f),
                        strokeWidth = 6f
                    )
                    DrawText(
                        value = cordinate.x.toString(),
                        color = cordinate.color,
                        textMeasurer = textMeasurer,
                        offset = Offset(cordinate.x + 5, 15f)
                    )
                }

                if (cordinate.y.toInt().mod(measureState.step) != 0) {
                    DrawMarkers(
                        color = cordinate.color,
                        start = Offset(0f, cordinate.y),
                        end = Offset(10f, cordinate.y),
                        strokeWidth = 6f
                    )
                    DrawText(
                        value = cordinate.y.toString(),
                        color = cordinate.color,
                        textMeasurer = textMeasurer,
                        offset = Offset(15f, cordinate.y)
                    )
                }
                drawCircle(
                    color = cordinate.color,
                    radius = cordinate.dotRadius,
                    center = Offset(cordinate.x, cordinate.y)
                )
            }
        }
    }
}

private fun DrawScope.DrawMarkers(color: Color, start: Offset, end: Offset, strokeWidth: Float) {
    drawLine(color = color, start = start, end = end, strokeWidth = strokeWidth)
}

private fun DrawScope.DrawText(
    value: String,
    color: Color = Color.LightGray,
    fontSize: TextUnit = 12.sp,
    textMeasurer: TextMeasurer,
    offset: Offset
) {
    val textLayoutResult: TextLayoutResult = textMeasurer.measure(
        text = value,
        style = TextStyle(color = color, fontSize = fontSize)
    )

    // Draw the text at the desired position
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = offset
    )
}

private fun DrawScope.DrawIntersection(
    color: Color,
    start: Offset,
    end: Offset,
    strokeWidth: Float
) {
    drawLine(color = color, start = start, end = end, strokeWidth = strokeWidth)
}