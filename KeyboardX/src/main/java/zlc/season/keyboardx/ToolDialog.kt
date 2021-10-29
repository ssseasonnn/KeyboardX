package zlc.season.keyboardx

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
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
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.flow.MutableStateFlow

class ToolDialog(context: Context) : Dialog(context) {
    val heightFlow = MutableStateFlow(0)

    init {
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setContentView(FrameLayout(context))
    }

    override fun show() {
        super.show()
        window?.let {
            val lp = it.attributes.apply {
                width = 0
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
        }
    }
}