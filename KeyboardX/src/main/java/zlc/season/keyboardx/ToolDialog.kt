package zlc.season.keyboardx

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.view.Gravity.LEFT
import android.view.Gravity.TOP
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager.LayoutParams.*
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowCompat.setDecorFitsSystemWindows
import androidx.core.view.WindowInsetsCompat.Type.ime
import kotlinx.coroutines.flow.MutableStateFlow

class ToolDialog private constructor(context: Context) : Dialog(context) {
    companion object {
        @Volatile
        private var INSTANCE: ToolDialog? = null

        fun getInstance(context: Context): ToolDialog =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ToolDialog(context).also { INSTANCE = it }
            }
    }

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
                width = 0
                height = MATCH_PARENT
                gravity = TOP or LEFT
                flags = FLAG_NOT_TOUCH_MODAL or FLAG_NOT_FOCUSABLE or
                        FLAG_ALT_FOCUSABLE_IM or FLAG_LAYOUT_IN_SCREEN
            }
            it.attributes = lp
            it.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
            it.setDimAmount(0f)

            setDecorFitsSystemWindows(it, true)
            setOnApplyWindowInsetsListener(it.decorView) { _, insets ->
                val imeHeight = insets.getInsets(ime()).bottom
                if (imeHeight > 0 && getLastHeight() != imeHeight) {
                    saveLastHeight(imeHeight)
                }
                heightFlow.tryEmit(imeHeight)
                insets
            }

            it.decorView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                it.decorView.requestApplyInsets()
            }
        }
    }

    fun checkKeyBoardState(): Boolean {
        val window = window ?: return false
        val insets = ViewCompat.getRootWindowInsets(window.decorView)
        return insets?.isVisible(ime()) ?: false
    }
}