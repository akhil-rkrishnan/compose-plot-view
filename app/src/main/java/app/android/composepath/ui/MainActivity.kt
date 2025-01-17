package app.android.composepath.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import app.android.composepath.data.model.MeasureState
import app.android.composepath.ui.canvas.MeasureView
import app.android.composepath.ui.core.theme.ComposePathTheme
import app.android.composepath.utils.hideStatusBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        enableEdgeToEdge()
        setContent {
            ComposePathTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val measureState = MeasureState(
                        step = 100,
                        labelCordinates = listOf(
                            /*LabelCordinates(
                                100f, 100f, Color.Magenta
                            ), LabelCordinates(
                                800f, 300f, Color.Blue
                            ), LabelCordinates(
                                500f, 100f, Color.Red
                            ), LabelCordinates(
                                501f, 350f, Color.Cyan
                            )*/
                        )
                    )
                    MeasureView(
                        measureState = measureState
                    ) {
                    }
                }
            }
        }
    }

}
