package com.unc0ded.listedapp.view.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unc0ded.listedapp.data.repository.LocalRepository
import com.unc0ded.listedapp.data.repository.Repository
import com.unc0ded.listedapp.launchIO
import com.unc0ded.listedapp.model.DashboardResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: Repository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val _dashboardResponse = MutableLiveData<DashboardResponse>()
    val dashboardResponse: LiveData<DashboardResponse>
        get() = _dashboardResponse

    init {
        viewModelScope.launchIO(
            action = {
                localRepository.saveAuthToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
                val dashboardData = repository.getDashboard()
                _dashboardResponse.postValue(dashboardData)
            },
            onError = {
                Log.e("DashboardViewModel", "#getDashboard: ${it.message}")
            }
        )
    }

//    fun getDashboard() {
//        viewModelScope.launchIO(
//            action = {
//                val dashboardData = repository.getDashboard()
//                _dashboardResponse.postValue(dashboardData)
//                Log.e("DashboardViewModel", "#getDashboard: success")
//            },
//            onError = {
//                Log.e("DashboardViewModel", "#getDashboard: ${it.message}")
//            }
//        )
//    }
}