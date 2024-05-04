package com.example.fitnesapp
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.fitnesapp.databinding.ActivityMainBinding
import com.example.fitnesapp.utils.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var tts: TextToSpeech
    private lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWithNavController(binding.bNav, findNavController(R.id.fragmentContainerView))

    }

}