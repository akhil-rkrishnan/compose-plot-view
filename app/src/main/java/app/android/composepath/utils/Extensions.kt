package app.android.composepath.utils

infix fun<T: Number> T.mods(value: T) : Number? {
    val mode: Number? = when (this) {
        is Float -> this % value.toFloat()
        is Int -> this % value.toInt()
        else -> null
    }
    return mode
}
