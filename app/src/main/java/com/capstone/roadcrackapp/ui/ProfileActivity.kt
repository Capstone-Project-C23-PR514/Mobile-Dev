package com.capstone.roadcrackapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.capstone.roadcrackapp.R
import com.capstone.roadcrackapp.databinding.ActivityProfileBinding
import com.capstone.roadcrackapp.model.sharedpreferences.LogPreferences

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.title = "Profile"
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.color_primary))

        binding.logoutbtn.setOnClickListener {
            logoutvalidate()
        }


        setupBottomNav()
    }

    private fun logoutvalidate() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Konfirmasi logout?")
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            logout()
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
        }
        builder.show()
    }
    private fun logout(){
        val loginPref = LogPreferences(this)
        loginPref.clearToken()
        val intent = Intent(this,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun setupBottomNav(){

        val bottomNav = binding.bottomBar
        bottomNav.selectedItemId = R.id.profile


        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }

                R.id.info -> {
                    startActivity(Intent(this, InfoActivity::class.java))
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
