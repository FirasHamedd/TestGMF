package com.example.testgmf.shared.data

import com.example.testgmf.data.model.CountryDataModel
import com.example.testgmf.data.model.SingleCountryDetailsDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(value = ServiceCatalog.GET_ALL_COUNTRIES)
    suspend fun fetchAllCountries(): Response<List<CountryDataModel>>

    @GET(value = ServiceCatalog.GET_SINGLE_COUNTRY_DETAILS)
    suspend fun fetchSingleCountry(
        @Path(value = PathParams.COUNTRY_NAME) countryName: String
    ): Response<List<SingleCountryDetailsDataModel>>
}