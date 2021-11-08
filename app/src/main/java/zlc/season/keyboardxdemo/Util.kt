package zlc.season.keyboardxdemo

import android.content.res.Resources

val Int.px
    get() = this * Resources.getSystem().displayMetrics.density + 0.5f