package zlc.season.keyboardxdemo

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.coroutines.launch
import zlc.season.animatorx.animHeight

class KeyboardLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    fun open(keyboardHeight: Int) {
        currentScope.launch {
            if (keyboardHeight == 0) {
                realAnimHeight(minimumHeight.toFloat())
            } else {
                realAnimHeight(keyboardHeight.toFloat())
            }
        }
    }

    fun close() {
        currentScope.launch {
            realAnimHeight(0f)
        }
    }

    fun animate(height: Float) {
        currentScope.launch {
            realAnimHeight(height)
        }
    }

    private suspend fun realAnimHeight(to: Float, duration: Long = 200) {
        animHeight(to = to, duration = duration)
    }
}