package com.capstone.roadcrackapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.roadcrackapp.model.repo.Repository
import com.capstone.roadcrackapp.model.remote.Result
import com.capstone.roadcrackapp.model.response.LoginUsers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {

    private val _responseLogin = repository.responseLogin
    val responseLogin: LiveData<Result<LoginUsers>> = _responseLogin
    fun login(email : String, password : String){
        viewModelScope.launch{
            repository.login(email, password)
        }
    }
}