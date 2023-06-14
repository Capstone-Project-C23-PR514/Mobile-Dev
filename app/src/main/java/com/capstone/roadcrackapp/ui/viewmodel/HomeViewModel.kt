package com.capstone.roadcrackapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capstone.roadcrackapp.model.repo.Repository
import com.capstone.roadcrackapp.model.remote.Result
import com.capstone.roadcrackapp.model.response.ReportsItem

class HomeViewModel(private val repository: Repository): ViewModel() {
    fun getReport() : LiveData<Result<List<ReportsItem>>> {
        return liveData {
            emit(Result.Loading)
            emitSource(repository.getReports())
        }
    }
//    fun getStoryPaging(): LiveData<PagingData<Story>> = repository.getStoryPaging().cachedIn(viewModelScope)
}