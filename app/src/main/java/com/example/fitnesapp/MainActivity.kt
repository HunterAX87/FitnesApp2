package com.example.fitnesapp
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.transition.Visibility
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

        val controller= findNavController(R.id.fragmentContainerView)
        setupWithNavController(binding.bNav, controller)
        controller.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){

                R.id.customDaysListFragment->{
                    binding.bNav.visibility= View.GONE
                }

                R.id.exercisesListFragment->{
                    binding.bNav.visibility= View.GONE
                }

                R.id.exercisesFragment->{
                    binding.bNav.visibility= View.GONE
                }

                R.id.dayFinishFragment->{
                    binding.bNav.visibility= View.GONE
                }
                else-> {
                    binding.bNav.visibility= View.VISIBLE
                }

            }

        }

    }

}