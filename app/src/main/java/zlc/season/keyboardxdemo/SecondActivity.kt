package zlc.season.keyboardxdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import zlc.season.keyboardxdemo.databinding.ActivityMainBinding
import zlc.season.keyboardxdemo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.input.setOnClickListener {
            InputDialog().show(supportFragmentManager, "")
        }
    }
}