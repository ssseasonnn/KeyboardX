package zlc.season.keyboardxdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
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

    fun showKeyboard() {
        binding.input.requestFocus()
        binding.input.post { keyboardX.showKeyboard(binding.input) }
    }

    private fun isEmojiSelected(): Boolean = binding.emojiIcon.isSelected

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (isInEditMode) return

        keyboardX.visibleFlow().onEach {
            if (!it && !isEmojiSelected()) {
                keyboardLayout.close()
            }
        }.launchIn(currentScope)

        keyboardX.heightFlow().onEach {
            if (it != 0 || !isEmojiSelected()) {
                keyboardLayout.animate(it.toFloat())
            }
        }.launchIn(currentScope)

        binding.input.setOnClickListener {
            if (binding.emojiIcon.isSelected) {
                binding.emojiIcon.isSelected = false
            }
            if (!keyboardX.isKeyboardShow()) {
                keyboardX.showKeyboard(binding.input)
            }
        }

        binding.emojiIcon.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                keyboardLayout.open(keyboardX.height())

                if (keyboardX.isKeyboardShow()) {
                    keyboardX.forceHideKeyboard(binding.input)
                }
            } else {
                keyboardX.showKeyboard(binding.input)
            }
        }
    }
}