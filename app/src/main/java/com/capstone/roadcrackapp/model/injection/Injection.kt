package com.capstone.roadcrackapp.model.injection

import android.content.Context
import com.capstone.roadcrackapp.model.network.ApiConfig
import com.capstone.roadcrackapp.model.network.ApiConfigs
import com.capstone.roadcrackapp.model.repo.Repository

object Injection {
    fun provideRepository(context: Context) : Repository {
        val apiService = ApiConfig.getApiService()
        return Repository(apiService, context)
    }

    fun provideRepositories(context: Context) : Repository {
        val apiService = ApiConfigs.getApiServices()
        return Repository(apiService, context)
    }
}