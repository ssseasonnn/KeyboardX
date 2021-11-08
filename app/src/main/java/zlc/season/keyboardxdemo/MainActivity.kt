package zlc.season.keyboardxdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

        binding.test.setOnClickListener {
            println(keyboardX.isKeyBoardShow())
        }

        keyboardX.heightFlow().onEach {
            if (it == 0 && !binding.inputView.isEmojiSelected()) {
                binding.keyboardLayout.close()
            }
        }.launchIn(lifecycleScope)

        binding.inputView.setInputClick {
            if (!keyboardX.isKeyBoardShow()) {
                keyboardX.showKeyBoard(binding.inputView)
            }
        }

        binding.inputView.setEmojiClick { isEmojiSelected ->
            if (isEmojiSelected) {
                binding.keyboardLayout.open()
                if (keyboardX.isKeyBoardShow()) {
                    keyboardX.hideKeyBoard(binding.inputView)
                }
            } else {
                keyboardX.showKeyBoard(binding.inputView)
            }
        }
    }
}