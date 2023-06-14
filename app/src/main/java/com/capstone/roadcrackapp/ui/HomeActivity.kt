package com.capstone.roadcrackapp.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.roadcrackapp.R
import com.capstone.roadcrackapp.databinding.ActivityHomeBinding
import com.capstone.roadcrackapp.model.adapter.ItemAdapter
import com.capstone.roadcrackapp.model.sharedpreferences.LogPreferences
import com.capstone.roadcrackapp.ui.viewmodel.HomeViewModel
import com.capstone.roadcrackapp.model.remote.Result
import com.capstone.roadcrackapp.model.response.ReportsItem
import com.capstone.roadcrackapp.ui.viewmodel.ViewModelFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var models: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels {models}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefLogin = LogPreferences(this)
        val token = prefLogin.getToken()

        if (token.isNullOrEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        models = ViewModelFactory.getInstance(this)
        homeViewModel.getReport().observe(this){report ->
            when(report){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val data = report.data
                    initList(data)
                    Log.d("data", data.toString())
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        setupBottomNav()
    }

    private fun initList(listStories: List<ReportsItem>) {
        val ItemAdapter = ItemAdapter(listStories, this, object : ItemAdapter.ReportListener {
            override fun onItemClicker(story: ReportsItem, ivStory: ImageView, tvName: TextView, tvDescription: TextView, tvDate : TextView) {
//                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
//                intent.putExtra("data", story)
//
//                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    this@MainActivity,
//                    Pair(ivStory, "imageViewStory"),
//                    Pair(tvName, "nameStory"),
//                    Pair(tvDate, "dateStory"),
//                    Pair(tvDescription, "descStory")
//                )
//                startActivity(intent, optionsCompat.toBundle())
            }
        })
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvView.adapter = ItemAdapter
        binding.rvView.layoutManager = layoutManager
    }

    private fun setupBottomNav(){

        val bottomNav = binding.bottomBar
        bottomNav.selectedItemId = R.id.home


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