package com.capstone.roadcrackapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.capstone.roadcrackapp.R
import com.capstone.roadcrackapp.databinding.ActivityDetailBinding
import com.capstone.roadcrackapp.model.adapter.formatDate
import com.capstone.roadcrackapp.model.response.ReportsItem

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()

        val report = intent?.getParcelableExtra<ReportsItem>("data")

        supportActionBar?.show()
        supportActionBar?.title = "Detail Report"
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.color_primary))

        supportActionBar?.show()
        supportActionBar?.title = "Report Detail"
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.color_primary))

        binding.apply {
            tittle.text = report?.judul
            tvJenis.text = report?.desc
            tvAkurasi.text = report?.akurasi
            tvLokasi.text = report?.lokasi
            tvDate.text = formatDate(report?.createdAt ?: "2023-05-05T10:30:00+08:00")
        }
        Glide.with(this).load(report?.gambar).into(binding.ivReport)

    }

    private fun setupBottomNav() {

        val bottomNav = binding.bottomBar

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