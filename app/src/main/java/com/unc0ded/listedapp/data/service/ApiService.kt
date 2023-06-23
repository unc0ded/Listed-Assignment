package com.unc0ded.listedapp.data.service

import com.unc0ded.listedapp.model.DashboardResponse
import retrofit2.http.GET

interface ApiService {
    @GET("dashboardNew")
    suspend fun fetchDashboardData(): DashboardResponse
}