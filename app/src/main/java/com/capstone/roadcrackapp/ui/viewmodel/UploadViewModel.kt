package com.capstone.roadcrackapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.roadcrackapp.model.remote.Result
import com.capstone.roadcrackapp.model.repo.Repository
import com.capstone.roadcrackapp.model.response.ResponseUpload
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel (private val repository: Repository): ViewModel() {
    private val _responseUpload = repository.responseUpload
    val responseUpload : LiveData<Result<ResponseUpload>> = _responseUpload

    suspend fun uploadStory(token:String, judul:RequestBody,file: MultipartBody.Part, lokasi:RequestBody){
        repository.uploadStory(token, judul, file, lokasi)
    }


}