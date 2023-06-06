package com.capstone.roadcrackapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.roadcrackapp.model.ResponseRegister
import com.capstone.roadcrackapp.model.remote.Result
import com.capstone.roadcrackapp.model.viewmodel.Repository
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository : Repository) : ViewModel() {
    private val _responseRegister = repository.responseRegister
    val responseRegister: LiveData<Result<ResponseRegister>> = _responseRegister
    fun register(name : String,email: String,password: String){
        viewModelScope.launch{
            repository.register(name,email, password)
        }

    }
}