package zlc.season.keyboardx

import android.content.Context.MODE_PRIVATE
import zlc.season.claritypotion.ClarityPotion.Companion.clarityPotion

const val KEY_SP_FILE = "zlc.season.keyboardx.preference"
const val KEY_HEIGHT = "last_keyboard_height"

private val sharedPreferences by lazy { clarityPotion.getSharedPreferences(KEY_SP_FILE, MODE_PRIVATE) }

internal fun saveLastHeight(height: Int) {
    with(sharedPreferences.edit()) {
        putInt(KEY_HEIGHT, height)
        apply()
    }
}

internal fun getLastHeight(): Int {
    return sharedPreferences.getInt(KEY_HEIGHT, 0)
}