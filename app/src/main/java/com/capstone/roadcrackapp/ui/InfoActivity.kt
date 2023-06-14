package com.capstone.roadcrackapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.roadcrackapp.R
import com.capstone.roadcrackapp.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()
    }
    private fun setupBottomNav(){

        val bottomNav = binding.bottomBar
        bottomNav.selectedItemId = R.id.info


        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.info -> {
                    startActivity(Intent(this, InfoActivity::class.java))
                    true
                }

                R.id.home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }

                R.id.alert -> {
                    startActivity(Intent(this, AlertActivity::class.java))
                    true
                }

                R.id.add -> {
                    startActivity(Intent(this, UploadActivity::class.java))
                    true
                }


                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                else -> false
            }
        }

    }
}