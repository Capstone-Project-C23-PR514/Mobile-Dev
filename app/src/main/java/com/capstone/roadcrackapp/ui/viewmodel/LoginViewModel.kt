package com.capstone.roadcrackapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.roadcrackapp.model.response.Users
import com.capstone.roadcrackapp.model.viewmodel.Repository
import com.capstone.roadcrackapp.model.remote.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {

    private val _responseLogin = repository.responseLogin
    val responseLogin: LiveData<Result<Users>> = _responseLogin
    fun login(email : String, password : String){
        viewModelScope.launch{
            repository.login(email, password)
        }
    }
}