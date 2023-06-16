package com.capstone.roadcrackapp.model.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.roadcrackapp.model.ResponseRegister
import com.capstone.roadcrackapp.model.network.ApiConfig
import com.capstone.roadcrackapp.model.network.ApiConfigs
import com.capstone.roadcrackapp.model.network.ApiService
import com.capstone.roadcrackapp.model.remote.Result
import com.capstone.roadcrackapp.model.response.LoginUsers
import com.capstone.roadcrackapp.model.response.ReportsItem
import com.capstone.roadcrackapp.model.response.ResponseUpload
import com.capstone.roadcrackapp.model.sharedpreferences.LogPreferences
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository(private val apiService: ApiService, private val context: Context) {
    private val _responseLogin = MutableLiveData<Result<LoginUsers>>()
    val responseLogin: LiveData<Result<LoginUsers>> = _responseLogin

    private val _responseRegister = MutableLiveData<Result<ResponseRegister>>()
    val responseRegister : LiveData<Result<ResponseRegister>> = _responseRegister

    private val _responseUpload = MutableLiveData<Result<ResponseUpload>>()
    val responseUpload : LiveData<Result<ResponseUpload>> = _responseUpload
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
                result?.loginUsers?.let{
                    _responseLogin.value = Result.Success(it)
                }
            } else {
                _responseLogin.value = Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            _responseLogin.value = Result.Error(e.message.toString())
        }
    }
    suspend fun getReports(): LiveData<Result<List<ReportsItem>>> {
        val result = MutableLiveData<Result<List<ReportsItem>>>()
        result.value = Result.Loading

        val token = LogPreferences(context).getToken()

        try {
            val response = apiService.getAllReports("$token")
            if (response.isSuccessful) {
                val Report = response.body()?.reports?: emptyList()
                result.value = Result.Success(Report)
                Log.d("debuug", "$response")
            } else {
                result.value = Result.Error("Error: ${response.code()}")
                Log.d("debuug", "$response")
            }
        }   catch (e: Exception) {
            result.value = Result.Error(e.message.toString())
            Log.d("debuug", "$e.message")
        }
        return result
    }

    suspend fun uploadStory(token:String, judul:RequestBody,file: MultipartBody.Part, lokasi:RequestBody){
        _responseUpload.value = Result.Loading
        try {
            val response = ApiConfigs.getApiServices().getUpload(token, judul, file, lokasi)
            if (response.isSuccessful){
                val result = response.body()
                if (result!=null){
                    _responseUpload.value = Result.Success(result)
                    Log.d("debuug", "Success:$response")
                }
            }else{
                _responseUpload.value = Result.Error("Error: ${response.code()}")
                Log.d("debuug", "else$response")
            }
        }catch (e:Exception){
            _responseUpload.value = Result.Error(e.message.toString())
            Log.d("debuug", "exception${e.message.toString()}")
        }
    }



}