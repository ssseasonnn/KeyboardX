package zlc.season.keyboardxdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.app.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlc.season.keyboardx.KeyboardX
import zlc.season.keyboardxdemo.databinding.LayoutInputViewBinding

class InputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val binding = LayoutInputViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val keyboardX by lazy { KeyboardX() }
    private val keyboardLayout by lazy {
        (parent as ViewGroup).findViewById<KeyboardLayout>(R.id.keyboard_layout)
    }
    private val coroutineScope by lazy { (context as ComponentActivity).lifecycleScope }

    private fun isEmojiSelected(): Boolean = binding.emojiIcon.isSelected

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        keyboardX.visibleFlow().onEach {
            if (!it && !isEmojiSelected()) {
                keyboardLayout.close()
            }
        }.launchIn(coroutineScope)

        binding.input.setOnClickListener {
            binding.input.requestFocus()
            if (binding.emojiIcon.isSelected) {
                binding.emojiIcon.isSelected = false
            }
            if (!keyboardX.isKeyboardShow()) {
                keyboardX.showKeyboard(this)
            }
        }

        binding.emojiIcon.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.input.requestFocus()
            if (it.isSelected) {
                keyboardLayout.open()
                if (keyboardX.isKeyboardShow()) {
                    keyboardX.hideKeyboard(this)
                }
            } else {
                keyboardX.showKeyboard(this)
            }
        }
    }
}