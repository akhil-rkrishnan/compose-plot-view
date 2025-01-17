package app.android.composepath.utils

import android.os.Build
import android.view.View
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity

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

fun Offset.isActual() : Boolean {
    return isValid() && x != 0f && y != 0f
}