package app.android.composepath.ui.canvas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay


@Composable
fun BasicPath(modifier: Modifier = Modifier){
    var target by remember {
        mutableFloatStateOf(0f)
    }
    val float = animateFloatAsState(targetValue = target)
    LaunchedEffect(Unit) {
        while (target < 500f) {
            target += 2f
            delay(100)
        }
    }
    Canvas(modifier) {
        val path = Path().apply {
            moveTo(100f, 100f)
            lineTo(100f, 500f)
            lineTo(500f, 500f)
            quadraticBezierTo(800f, 300f, 500f, 100f)
            close()
        }
        drawPath(path, color = Color.Red, style = Stroke(width = 6.dp.toPx()))
    }
}