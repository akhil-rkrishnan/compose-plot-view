package app.android.composepath.utils

import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.compose.ui.geometry.Offset
import java.text.DecimalFormat

private const val TAG = "Extensions"
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

fun Offset?.isActual(): Boolean {
    return this != null && isValid() && x != 0f && y != 0f
}

fun Number.trimDecimals(): String {
    try {
        return DecimalFormat("#.0").format(this)
    } catch (exception: Exception) {
        Log.e(TAG, "trimDecimals: ${exception.printStackTrace()}")
        return toString()
    }
}