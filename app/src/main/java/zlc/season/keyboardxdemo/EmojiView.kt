package zlc.season.keyboardxdemo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.app.ComponentActivity
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import zlc.season.animatorx.animHeight
import zlc.season.animatorx.animMarginBottom
import zlc.season.animatorx.animTop
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
    val scope by lazy { (context as ComponentActivity).lifecycleScope }

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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        keyboardX.heightFlow().onEach {
//            var marginBottom = if (it > 0) it.toFloat() else (-300).px
//            println("marginBottom: $marginBottom")
            println("imeHeight:$it")
            (binding.root.parent as View).animHeight(it.toFloat())
//            (binding.root.parent as View).post {
//                println((binding.root.parent as View).height)
//            }
            isShow = it > 0
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
            scope.launch {
                (binding.root.parent as View).animHeight(300.px)
                isShow = true
            }
        } else {
            scope.launch {
                (binding.root.parent as View).animHeight(imeHeight.toFloat())
                isShow = true
            }
        }
    }

    fun hide() {
        scope.launch {
            (binding.root.parent as View).animHeight(0f)
            isShow = false
        }
    }
}

class EmojiItem(val emoji: String) : YashaItem