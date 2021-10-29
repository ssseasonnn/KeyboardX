package zlc.season.keyboardx

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.Gravity.LEFT
import android.view.Gravity.TOP
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.*
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.concurrent.thread

class ToolDialog(context: Context) : Dialog(context) {
    val heightFlow = MutableStateFlow(0)

    init {
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setContentView(FrameLayout(context).apply { setBackgroundColor(Color.RED) })
    }

    override fun show() {
        super.show()
        window?.let {
            val lp = it.attributes.apply {
                width = 100
                height = MATCH_PARENT
                gravity = TOP or LEFT
                flags = FLAG_NOT_TOUCH_MODAL or FLAG_NOT_FOCUSABLE or
                        FLAG_ALT_FOCUSABLE_IM or FLAG_LAYOUT_IN_SCREEN
            }
            it.attributes = lp
            it.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
            it.setDimAmount(0f)

            WindowCompat.setDecorFitsSystemWindows(it, false)
            ViewCompat.setOnApplyWindowInsetsListener(it.decorView) { _, insets ->
                val imeHeight = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                heightFlow.tryEmit(imeHeight)
                insets
            }
            ViewCompat.setWindowInsetsAnimationCallback(it.decorView, object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
                override fun onProgress(insets: WindowInsetsCompat, runningAnimations: MutableList<WindowInsetsAnimationCompat>): WindowInsetsCompat {
                    println("on progress")
                    return insets
                }
            })

            it.decorView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                it.decorView.requestApplyInsets()
            }
        }
    }
}