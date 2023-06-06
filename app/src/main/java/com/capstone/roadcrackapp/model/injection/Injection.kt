package com.capstone.roadcrackapp.model.injection

import android.content.Context
import com.capstone.roadcrackapp.model.network.ApiConfig
import com.capstone.roadcrackapp.model.viewmodel.Repository

object Injection {
    fun provideRepository(context: Context) : Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService, context)
    }
}