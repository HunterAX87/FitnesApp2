package com.example.fitnesapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fitnesapp.databinding.ActivityMainBinding
import com.example.fitnesapp.fragments.DaysFragment
import com.example.fitnesapp.utils.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model.pref=getSharedPreferences("main", MODE_PRIVATE)
        openFragment(DaysFragment.newInstance())
    }

    override fun onBackPressed() {
        if(currentFragment is DaysFragment) super.onBackPressed()
        else openFragment(DaysFragment.newInstance())

    }


}