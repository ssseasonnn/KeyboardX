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

    fun setEmojiClick(block: () -> Unit) {
        binding.emojiIcon.setOnClickListener { block() }
    }
}