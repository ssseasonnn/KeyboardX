package zlc.season.keyboardx

import android.view.View
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow
import zlc.season.claritypotion.ClarityPotion

class KeyboardX {
    private var toolDialogPopup: ToolDialog

    init {
        val current = ClarityPotion.currentActivity()
        if (current == null) {
            throw IllegalStateException("No activity found! Should be called after Activity onCreate method!")
        } else if (current !is ComponentActivity) {
            throw IllegalStateException("Need ComponentActivity!")
        }

        toolDialogPopup = ToolDialog(current)

        current.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_CREATE) {
                    toolDialogPopup.show()
                    println("show")
                } else if (event == Lifecycle.Event.ON_DESTROY) {
                    toolDialogPopup.dismiss()
                    println("dismiss")
                }
            }
        })
    }

    fun height(): Int {
        return toolDialogPopup.heightFlow.value
    }

    fun heightFlow(): Flow<Int> {
        return toolDialogPopup.heightFlow
    }

    fun showKeyBoard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.show(WindowInsetsCompat.Type.ime())
    }

    fun hideKeyBoard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
    }
}
