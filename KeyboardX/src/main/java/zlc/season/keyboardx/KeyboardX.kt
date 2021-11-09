package zlc.season.keyboardx

import android.view.View
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

    fun heightFlow(): Flow<Int> {
        return dialog.heightFlow
    }

    fun showKeyBoard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.show(WindowInsetsCompat.Type.ime())
    }

    fun hideKeyBoard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
    }
}
