package com.unc0ded.listedapp.data.service

import com.unc0ded.listedapp.data.repository.LocalRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val repository: LocalRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authToken = runBlocking {
            repository.getAuthToken()
        }
        return chain.proceed(
            if (authToken.isNotEmpty()) request.newBuilder()
                .header("Authorization", "Bearer $authToken")
                .build()
            else request
        )
    }
}