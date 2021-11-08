package zlc.season.keyboardxdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import zlc.season.keyboardxdemo.databinding.LayoutInputViewBinding

class InputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    val binding = LayoutInputViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun isEmojiSelected(): Boolean = binding.emojiIcon.isSelected

    fun setEmojiClick(block: (Boolean) -> Unit) {
        binding.emojiIcon.setOnClickListener {
            it.isSelected = !it.isSelected
            binding.input.requestFocus()
            block(it.isSelected)
        }
    }

    fun setInputClick(block: () -> Unit) {
        binding.input.setOnClickListener {
            binding.input.requestFocus()
            if (binding.emojiIcon.isSelected) {
                binding.emojiIcon.isSelected = false
            }
            block()
        }
    }
}