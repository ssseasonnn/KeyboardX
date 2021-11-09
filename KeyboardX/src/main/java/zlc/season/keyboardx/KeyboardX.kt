package zlc.season.keyboardx

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow
import zlc.season.claritypotion.ClarityPotion

class KeyboardX {
    private var dialog: ToolDialog

    init {
        val current = ClarityPotion.currentActivity()
        if (current == null) {
            throw IllegalStateException("No activity found! Should be called after Activity onCreate method!")
        } else if (current !is ComponentActivity) {
            throw IllegalStateException("Need ComponentActivity!")
        }

        dialog = ToolDialog(current)

        current.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == ON_CREATE) {
                    dialog.show()
                } else if (event == ON_DESTROY) {
                    dialog.dismiss()
                }
            }
        })
    }

    /**
     * Flow with keyboard height
     */
    fun heightFlow(): Flow<Int> {
        return dialog.heightFlow
    }

    /**
     * Return keyboard height
     */
    fun height(): Int {
        return getLastHeight()
    }

    /**
     * Flow with keyboard visible
     */
    fun visibleFlow(): Flow<Boolean> {
        return dialog.visibleFlow
    }

    fun isKeyboardShow(): Boolean {
        return dialog.checkKeyboardState()
    }

    /**
     * Show keyboard, need focus
     */
    fun showKeyboard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.show(WindowInsetsCompat.Type.ime())
    }

    /**
     * Hide keyboard, need focus
     */
    fun hideKeyboard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
    }

    /**
     * Force show keyboard,no need focus
     */
    fun forceShowKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun forceHideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(0, 0)
    }
}
