package com.capstone.roadcrackapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.capstone.roadcrackapp.R
import com.capstone.roadcrackapp.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.show()
        supportActionBar?.title = "Guide Book"
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.color_primary))

        setupBottomNav()

        val text1 = "Citra gambar atau proses pengambilan \n" +
                "gambar harus dilakukan saat jalan telah disinari matahari agar proses pengambilan citra menjadi jelas."
        val text2 = "Untuk pengambilan citra retakan mengambil sampel gambar di jalan dengan posisi tegak lurus dengan kemiringan 90 derajat serta ketinggian 1 meter dari permukaan retakan."
        val text3 = "Pengambilan citra menggunakan kamera dengan persyaratan minimal berukuran 13 mp agar hasil yang dihasilkan jelas dan tidak blur karena akan mempengaruhi proses klasifikasi."

        binding.text1.text = text1
        binding.text2.text = text2
        binding.text3.text = text3
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