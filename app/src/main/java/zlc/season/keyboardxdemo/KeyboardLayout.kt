package zlc.season.keyboardxdemo

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.app.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import zlc.season.animatorx.animHeight
import zlc.season.keyboardx.KeyboardX

class KeyboardLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val keyboardX by lazy { KeyboardX() }
    private val coroutineScope by lazy { (context as ComponentActivity).lifecycleScope }

    private var heightJob: Job? = null
    private var openJob: Job? = null
    private var closeJob: Job? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isInEditMode) {
            heightJob = keyboardX.heightFlow().onEach {
                if (it > 0) {
                    openJob?.cancel()
                    closeJob?.cancel()
                    realAnimHeight(it.toFloat())
                }
            }.launchIn(coroutineScope)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (!isInEditMode) {
            heightJob?.cancel()
        }
    }

    fun isOpen(): Boolean {
        return height > 0
    }

    fun changeState() {
        if (isOpen()) {
            close()
        } else {
            open()
        }
    }

    fun open() {
        closeJob?.cancel()
        openJob?.cancel()
        openJob = coroutineScope.launch {
            val imeHeight = keyboardX.height()
            if (imeHeight == 0) {
                realAnimHeight(minimumHeight.toFloat())
            } else {
                realAnimHeight(imeHeight.toFloat())
            }
        }
    }

    fun close() {
        openJob?.cancel()
        closeJob?.cancel()
        closeJob = coroutineScope.launch {
            realAnimHeight(0f)
        }
    }

    private suspend fun realAnimHeight(to: Float, duration: Long = 200) {
        animHeight(to = to, duration = duration)
    }
}