package com.example.testgmf.shared.data.utils

import retrofit2.Response
import retrofit2.http.GET

interface TestingService {
    @GET
    suspend fun callTestingService(): Response<String>
}
