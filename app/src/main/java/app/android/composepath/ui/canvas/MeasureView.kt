package app.android.composepath.ui.canvas

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.android.composepath.data.model.MeasureState
import app.android.composepath.utils.isActual
import app.android.composepath.utils.trimDecimals
import app.android.composepath.R


private const val TAG = "MeasureView"

@Composable
fun MeasureView(
    modifier: Modifier = Modifier.fillMaxSize(),
    measureState: MeasureState = MeasureState(),
    view: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val width = configuration.screenWidthDp * density
    val height = configuration.screenHeightDp * density

    var dragOffset by remember {
        mutableStateOf<Offset?>(null)
    }

    Log.d(TAG, "Screen width and height => $width, $height")
    val textMeasurer = rememberTextMeasurer()
    Box(modifier = modifier.pointerInput(Unit, Unit, {
        detectTapGestures(onDoubleTap = { offset ->
            dragOffset = null
        }, onTap = { offset ->
            if (!dragOffset.isActual()) {
                dragOffset = offset
            }
        }, onLongPress = {

        })
    })) {
        view.invoke()
        AnimatedVisibility(
            visible = dragOffset.isActual(),
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            val dpX = with(LocalDensity.current) { dragOffset?.x?.toDp() ?: 0.dp }
            val dpY = with(LocalDensity.current) { dragOffset?.y?.toDp() ?: 0.dp }
            Box(
                modifier = Modifier
                    .offset(dpX, dpY)
                    .background(Color.Red)
                    .pointerInput(Unit, Unit) {
                        detectDragGestures(onDrag = { pic, offset ->
                            pic.consume()
                            dragOffset = dragOffset?.let {
                                it + pic.position
                            }
                        })
                    }
                    .animateContentSize(animationSpec = tween(200, 100))
            )
        }
        Canvas(modifier = Modifier) {
            if (measureState.hoverCrossOver.isEnabled) {
                DynamicHover(
                    dragOffset = dragOffset,
                    measureState = measureState,
                    textMeasurer = textMeasurer
                )
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
                        style = TextStyle(color = Color.Black),
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
                        style = TextStyle(color = Color.Black),
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
                    for (y in 0 until cordinate.y.toInt() step measureState.dashStep) {
                        DrawIntersection(
                            cordinate.color,
                            start = Offset(cordinate.x, y.toFloat()),
                            end = Offset(cordinate.x, (y + (dashStep / 2)).toFloat()),
                            strokeWidth = 3f
                        )
                    }
                    for (x in 0 until cordinate.x.toInt() step measureState.dashStep) {
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
                        style = TextStyle(color = cordinate.color),
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
                        style = TextStyle(color = cordinate.color),
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
    textMeasurer: TextMeasurer,
    style: TextStyle = TextStyle(
        color = Color.LightGray,
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(resId = R.font.roboto_regular))
    ),
    offset: Offset
) {
    val textLayoutResult: TextLayoutResult = textMeasurer.measure(
        text = value,
        style = style
    )

    // Draw the text at the desired position
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = offset
    )
}

private fun DrawScope.DrawText(
    value: AnnotatedString,
    textMeasurer: TextMeasurer,
    style: TextStyle = TextStyle(
        color = Color.LightGray,
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(resId = R.font.roboto_regular))
    ),
    offset: Offset
) {
    val textLayoutResult: TextLayoutResult = textMeasurer.measure(
        text = value,
        style = style
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

private fun DrawScope.DynamicHover(
    dragOffset: Offset?,
    measureState: MeasureState,
    textMeasurer: TextMeasurer
) {
    if (dragOffset == null) return
    val actualX = dragOffset.x
    val actualY = dragOffset.y
    for (y in 0 until actualY.toInt() step measureState.dashStep) {
        DrawIntersection(
            color = Color.LightGray,
            start = Offset(actualX, y.toFloat()),
            end = Offset(
                actualX,
                (y + (measureState.dashStep / 2)).toFloat()
            ),
            strokeWidth = 3f
        )
    }
    for (x in 0 until actualX.toInt() step measureState.dashStep) {
        DrawIntersection(
            color = Color.LightGray,
            start = Offset(x.toFloat(), actualY),
            end = Offset(
                (x + (measureState.dashStep / 2)).toFloat(),
                actualY
            ),
            strokeWidth = 3f
        )
    }
    drawCircle(color = Color.Black, radius = 10f, center = dragOffset)
    val annotatedString = buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Red)) {
            append("(X: ")
        }
        append(actualX.trimDecimals())
        append(", ")
        withStyle(SpanStyle(color = Color(0xff4eb175))){
            append("Y: ")
        }
        append(actualY.trimDecimals())
        append(")")
    }
    DrawText(
        value = annotatedString,
        style = TextStyle(
            color = measureState.hoverCrossOver.color,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular))
        ),
        textMeasurer = textMeasurer,
        offset = Offset(actualX + 15f, actualY)
    )
}