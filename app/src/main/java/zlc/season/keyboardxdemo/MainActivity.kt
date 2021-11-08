package zlc.season.keyboardxdemo

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import zlc.season.keyboardx.KeyboardX
import zlc.season.keyboardxdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val keyboardX by lazy { KeyboardX() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.inputView.setEmojiClick {
            binding.emojiView.changeState()
        }

//        keyboardX.heightFlow().onEach {
//            binding.inputView.updateLayoutParams<ViewGroup.MarginLayoutParams> { bottomMargin = it }
//        }.launchIn(lifecycleScope)
    }
}