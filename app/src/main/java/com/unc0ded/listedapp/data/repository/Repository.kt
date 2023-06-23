package com.unc0ded.listedapp.data.repository

import com.unc0ded.listedapp.data.service.ApiService
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun getDashboard() = apiService.fetchDashboardData()
}