package zlc.season.keyboardxdemo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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

        binding.inputView.post {
//            binding.input.requestFocus()
//            keyboardX.showKeyBoard(binding.inputView)
        }

        lifecycleScope.launch {
            keyboardX.heightFlow()
                .onEach {
                    println(it)
                    binding.inputView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        bottomMargin = it
                    }
                }.collect()
        }
    }
}