package zlc.season.keyboardxdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import zlc.season.keyboardxdemo.databinding.ItemEmojiBinding
import zlc.season.keyboardxdemo.databinding.LayoutEmojiViewBinding
import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem
import zlc.season.yasha.grid

class EmojiView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val binding = LayoutEmojiViewBinding.inflate(LayoutInflater.from(context), this, true)
    private val dataSource = YashaDataSource()

    init {
        binding.recyclerView.grid(dataSource) {
            spanCount(7)
            renderBindingItem<EmojiItem, ItemEmojiBinding> {
                onBind {
                    itemBinding.text.text = data.emoji
                }
            }
        }

        dataSource.addItems(EmojiData.data.map { EmojiItem(it) })
    }
}

class EmojiItem(val emoji: String) : YashaItem