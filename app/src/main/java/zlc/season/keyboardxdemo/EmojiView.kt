package zlc.season.keyboardxdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.app.ComponentActivity
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlc.season.keyboardx.KeyboardX
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

    private val keyboardX = KeyboardX()

    var isShow = false

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

        keyboardX.heightFlow().onEach {
            binding.root.updateLayoutParams<MarginLayoutParams> {
                height = it
            }
        }.launchIn((context as ComponentActivity).lifecycleScope)
    }

    fun changeState() {
        if (isShow) {
            hide()
        } else {
            show()
        }
    }

    fun show() {
        val imeHeight = keyboardX.height()
        if (imeHeight == 0) {
            binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
                height = 500
            }
        } else {
            binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
                height = imeHeight
            }
        }
        isShow = true
    }

    fun hide() {
        binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
            height = 0
        }
        isShow = false
    }
}

class EmojiItem(val emoji: String) : YashaItem