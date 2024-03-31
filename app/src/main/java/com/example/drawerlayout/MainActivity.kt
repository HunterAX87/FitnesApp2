package com.example.drawerlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.drawerlayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.item1-> Toast.makeText(this@MainActivity,"item 1", Toast.LENGTH_SHORT).show()
                    R.id.item2-> Toast.makeText(this@MainActivity,"item 2", Toast.LENGTH_SHORT).show()
                }
                drawer.closeDrawer(GravityCompat.START)
                true
            }

            open.setOnClickListener{
            drawer.openDrawer(GravityCompat.START)
            }
        }

    }
}