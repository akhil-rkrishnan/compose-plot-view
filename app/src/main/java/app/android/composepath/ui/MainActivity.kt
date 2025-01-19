package app.android.composepath.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import app.android.composepath.ui.core.theme.ComposePathTheme
import app.android.composepath.utils.hideStatusBar
import app.android.pointwise.data.model.PointState
import app.android.pointwise.ui.PointWiser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        enableEdgeToEdge()
        setContent {
            ComposePathTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val pointState = PointState(
                        step = 100,
                    )
                    PointWiser(
                        pointState = pointState
                    ) {

                    }
                }
            }
        }
    }
}
