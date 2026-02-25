package com.example.testgmf.shared.data

import com.example.testgmf.data.model.CountryDataModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(value = ServiceCatalog.GET_ALL_COUNTRIES)
    suspend fun fetchAllCountries(): Response<List<CountryDataModel>>
}