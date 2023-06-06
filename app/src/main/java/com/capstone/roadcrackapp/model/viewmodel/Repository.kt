package com.capstone.roadcrackapp.model.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.roadcrackapp.model.ResponseRegister
import com.capstone.roadcrackapp.model.network.ApiConfig
import com.capstone.roadcrackapp.model.network.ApiService
import com.capstone.roadcrackapp.model.remote.Result
import com.capstone.roadcrackapp.model.response.Users

class Repository(private val apiService: ApiService, private val context: Context) {
    private val _responseLogin = MutableLiveData<Result<Users>>()
    val responseLogin: LiveData<Result<Users>> = _responseLogin

    private val _responseRegister = MutableLiveData<Result<ResponseRegister>>()
    val responseRegister : LiveData<Result<ResponseRegister>> = _responseRegister

    suspend fun register(name : String,email: String,password: String){
        _responseRegister.value = Result.Loading
        try {
            val response = ApiConfig.getApiService().postRegister(name, email, password)
            if (response.isSuccessful) {
                val result = response.body()
                result?.let{
                    _responseRegister.value = Result.Success(it)
                }
            } else {
                _responseRegister.value = Result.Error("Error:${response.code()}")
            }
        } catch (e: Exception) {
            _responseRegister.value = Result.Error(e.message.toString())
        }
    }
    suspend fun login(email : String, password : String){
        try {
            val response = ApiConfig.getApiService().postLogin(email, password)
            if (response.isSuccessful) {
                val result = response.body()
                result?.users?.let{
                    _responseLogin.value = Result.Success(it)
                }
            } else {
                _responseLogin.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseLogin.value = Result.Error(e.message.toString())
        }
    }

}