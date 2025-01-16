package app.android.composepath.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.view.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.android.composepath.ui.canvas.BasicPath
import app.android.composepath.ui.canvas.LabelCordinates
import app.android.composepath.ui.canvas.MeasureState
import app.android.composepath.ui.canvas.MeasureView
import app.android.composepath.ui.core.theme.ComposePathTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        enableEdgeToEdge()
        setContent {
            ComposePathTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val labelCordinates = arrayListOf<LabelCordinates>()
                    (0..2).forEach {
                        val x = (100..2500).random().toFloat()
                        val y = (100..1500).random().toFloat()
                        labelCordinates.add(
                            LabelCordinates(x,y,listOf<Color>(Color.Green, Color.Blue, Color.Black,
                                Color.Magenta).random().copy(alpha = 0.5f), showPointIntersection = true)
                        )
                    }
                    val measureState = MeasureState(
                        step = 250,
                        labelCordinates = listOf(
                            LabelCordinates(
                                100f, 100f
                            ), LabelCordinates(
                                800f, 300f, Color.Blue
                            ), LabelCordinates(
                                500f, 100f, Color.Green
                            ), LabelCordinates(
                                501f, 350f, Color.Green
                            )
                        )
                    )
                    MeasureView(
                        measureState = measureState
                    ) {
                        BasicPath()
                    }
                }
            }
        }
    }

}

fun ComponentActivity.hideStatusBar() {
    window.decorView.post {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
}