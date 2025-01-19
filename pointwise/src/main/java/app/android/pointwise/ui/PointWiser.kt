package app.android.pointwise.ui

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
import app.android.composepath.utils.isActual
import app.android.composepath.utils.trimDecimals
import app.android.pointwise.R
import app.android.pointwise.data.model.PointState

/**
 * Composable for the point wiser
 * @param modifier Modifier for the composable @see[Modifier]
 * @param pointState Point state of the current context @see[PointState]
 * @param view The composable user want to render
 */
@Composable
fun PointWiser(
    modifier: Modifier = Modifier,
    pointState: PointState = PointState(),
    view: (@Composable () -> Unit)? = null
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val width = configuration.screenWidthDp * density
    val height = configuration.screenHeightDp * density

    var dragOffset by remember {
        mutableStateOf<Offset?>(null)
    }

    val textMeasurer = rememberTextMeasurer()
    // Box frame for canvas
    Box(modifier = modifier.pointerInput(Unit, Unit, {
        detectTapGestures(onDoubleTap = { offset ->
            dragOffset = null
        }, onTap = { offset ->
            if (!dragOffset.isActual()) {
                dragOffset = offset
            }
        })
    })) {
        view?.invoke()
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
            if (pointState.hoverCrossOver.isEnabled) {
                DynamicHover(
                    dragOffset = dragOffset,
                    pointState = pointState,
                    textMeasurer = textMeasurer
                )
            }
            //Drawing x axis
            for (axisValue in pointState.step until width.toInt() step pointState.step) {
                val mainSplit = (axisValue.mod(pointState.step * 2)) == 0
                if (mainSplit) {
                    DrawMarkers(
                        color = Color.DarkGray,
                        start = Offset(axisValue.toFloat(), 0f),
                        end = Offset(axisValue.toFloat(), 25f),
                        strokeWidth = 5f
                    )
                    if (pointState.showIntersection) {
                        DrawIntersection(
                            color = Color.Black.copy(0.1f),
                            start = Offset(axisValue.toFloat(), 0f),
                            end = Offset(axisValue.toFloat(), height),
                            strokeWidth = 3f
                        )
                    }
                    DrawText(
                        text = "${axisValue}x",
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
                    if (pointState.showIntersection) {
                        DrawIntersection(
                            color = Color.Black.copy(0.1f),
                            start = Offset(axisValue.toFloat(), 0f),
                            end = Offset(axisValue.toFloat(), height),
                            strokeWidth = 3f
                        )
                    }
                    DrawText(
                        text = "${axisValue}x",
                        textMeasurer = textMeasurer,
                        offset = Offset(axisValue.toFloat() - 23, 30f)
                    )
                }
            }
            // Drawing y axis
            for (axisValue in pointState.step until height.toInt() step pointState.step) {
                val mainSplit = (axisValue.mod(pointState.step * 2)) == 0
                if (mainSplit) {
                    DrawMarkers(
                        color = Color.DarkGray,
                        start = Offset(0f, axisValue.toFloat()),
                        end = Offset(25f, axisValue.toFloat()),
                        strokeWidth = 5f
                    )
                    if (pointState.showIntersection) {
                        DrawIntersection(
                            color = Color.Black.copy(alpha = 0.1f),
                            start = Offset(0f, axisValue.toFloat()),
                            end = Offset(width, axisValue.toFloat()),
                            strokeWidth = 1f
                        )
                    }
                    DrawText(
                        text = "${axisValue}y",
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
                    if (pointState.showIntersection) {
                        DrawIntersection(
                            color = Color.Black.copy(alpha = 0.1f),
                            start = Offset(0f, axisValue.toFloat()),
                            end = Offset(width, axisValue.toFloat()),
                            strokeWidth = 1f
                        )
                    }
                    DrawText(
                        text = "${axisValue}y",
                        textMeasurer = textMeasurer,
                        offset = Offset(30f, axisValue.toFloat() - 10)
                    )
                }
            }
            // Drawing label intersections
            pointState.labelCordinates.forEach { cordinate ->
                if (cordinate.showPointIntersection) {
                    val dashStep = 20
                    for (y in 0 until cordinate.y.toInt() step pointState.dashStep) {
                        DrawIntersection(
                            cordinate.color,
                            start = Offset(cordinate.x, y.toFloat()),
                            end = Offset(cordinate.x, (y + (dashStep / 2)).toFloat()),
                            strokeWidth = 3f
                        )
                    }
                    for (x in 0 until cordinate.x.toInt() step pointState.dashStep) {
                        DrawIntersection(
                            cordinate.color,
                            start = Offset(x.toFloat(), cordinate.y),
                            end = Offset((x + (dashStep / 2)).toFloat(), cordinate.y),
                            strokeWidth = 3f
                        )
                    }
                }
                if (cordinate.x.toInt().mod(pointState.step) != 0) {
                    DrawMarkers(
                        color = cordinate.color,
                        start = Offset(cordinate.x, 0f),
                        end = Offset(cordinate.x, 10f),
                        strokeWidth = 6f
                    )
                    DrawText(
                        text = cordinate.x.toString(),
                        style = TextStyle(color = cordinate.color),
                        textMeasurer = textMeasurer,
                        offset = Offset(cordinate.x + 5, 15f)
                    )
                }

                if (cordinate.y.toInt().mod(pointState.step) != 0) {
                    DrawMarkers(
                        color = cordinate.color,
                        start = Offset(0f, cordinate.y),
                        end = Offset(10f, cordinate.y),
                        strokeWidth = 6f
                    )
                    DrawText(
                        text = cordinate.y.toString(),
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

/**
 * Method to draw the markers in the screen
 * @param color Color of the marker @see[Color]
 * @param start Starting offset of the marker @see[Offset]
 * @param end Ending offset of the marker @see[Offset]
 * @param strokeWidth Stroke of the marker offset of the marker
 */
private fun DrawScope.DrawMarkers(color: Color, start: Offset, end: Offset, strokeWidth: Float) {
    drawLine(color = color, start = start, end = end, strokeWidth = strokeWidth)
}

/**
 * Method to draw the text in the screen
 * @param text Text to display on screen
 * @param color Color of the marker @see[Color]
 * @param textMeasurer Text measurer for the text @see[TextMeasurer]
 * @param style Style of the text @see[TextStyle]
 * @param offset Position where the text to placed in screen @see[Offset]
 */
private fun DrawScope.DrawText(
    text: String,
    textMeasurer: TextMeasurer,
    style: TextStyle = TextStyle(
        color = Color.LightGray,
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(resId = R.font.roboto_regular))
    ),
    offset: Offset
) {
    val textLayoutResult: TextLayoutResult = textMeasurer.measure(
        text = text,
        style = style
    )

    // Draw the text at the desired position
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = offset
    )
}

/**
 * Method to draw the text in the screen
 * @param text Annotated text to display on screen @see[AnnotatedString]
 * @param color Color of the marker @see[Color]
 * @param textMeasurer Text measurer for the text @see[TextMeasurer]
 * @param style Style of the text @see[TextStyle]
 * @param offset Position where the text to placed in screen @see[Offset]
 */
private fun DrawScope.DrawText(
    text: AnnotatedString,
    textMeasurer: TextMeasurer,
    style: TextStyle = TextStyle(
        color = Color.LightGray,
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(resId = R.font.roboto_regular))
    ),
    offset: Offset
) {
    val textLayoutResult: TextLayoutResult = textMeasurer.measure(
        text = text,
        style = style
    )

    // Draw the text at the desired position
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = offset
    )
}

/**
 * Method to draw the intersection in the screen
 * @param color Color of the marker @see[Color]
 * @param start Starting offset of the marker @see[Offset]
 * @param end Ending offset of the marker @see[Offset]
 * @param strokeWidth Stroke of the marker offset of the marker
 */
private fun DrawScope.DrawIntersection(
    color: Color,
    start: Offset,
    end: Offset,
    strokeWidth: Float
) {
    drawLine(color = color, start = start, end = end, strokeWidth = strokeWidth)
}

/**
 * Method to draw the dynamic hover to identify the points in the screen
 * @param dragOffset Current offset of screen @see[Offset]
 * @param pointState Point state of the current context @see[PointState]
 * @param textMeasurer Text measurer for the text @see[TextMeasurer]
 */
private fun DrawScope.DynamicHover(
    dragOffset: Offset?,
    pointState: PointState,
    textMeasurer: TextMeasurer
) {
    if (dragOffset == null) return
    val actualX = dragOffset.x
    val actualY = dragOffset.y
    for (y in 0 until actualY.toInt() step pointState.dashStep) {
        DrawIntersection(
            color = Color.LightGray,
            start = Offset(actualX, y.toFloat()),
            end = Offset(
                actualX,
                (y + (pointState.dashStep / 2)).toFloat()
            ),
            strokeWidth = 3f
        )
    }
    for (x in 0 until actualX.toInt() step pointState.dashStep) {
        DrawIntersection(
            color = Color.LightGray,
            start = Offset(x.toFloat(), actualY),
            end = Offset(
                (x + (pointState.dashStep / 2)).toFloat(),
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
        withStyle(SpanStyle(color = Color(0xff4eb175))) {
            append("Y: ")
        }
        append(actualY.trimDecimals())
        append(")")
    }
    DrawText(
        text = annotatedString,
        style = TextStyle(
            color = pointState.hoverCrossOver.color,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular))
        ),
        textMeasurer = textMeasurer,
        offset = Offset(actualX + 15f, actualY)
    )
}