package zlc.season.keyboardxdemo

import android.content.res.Resources
import android.view.View
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

val Int.px
    get() = this * Resources.getSystem().displayMetrics.density + 0.5f

val View.currentScope: CoroutineScope
    get() {
        val lifecycleOwner = ViewTreeLifecycleOwner.get(this) ?: throw IllegalStateException("LifecycleOwner not found")
        return lifecycleOwner.lifecycleScope
    }