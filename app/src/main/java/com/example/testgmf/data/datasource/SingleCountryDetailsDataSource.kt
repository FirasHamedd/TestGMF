package com.example.testgmf.data.datasource

import com.example.testgmf.data.model.SingleCountryDetailsDataModel
import com.example.testgmf.shared.data.ApiService
import com.example.testgmf.shared.data.utils.safeCall
import com.example.testgmf.shared.domain.Result
import com.example.testgmf.shared.domain.Error
import javax.inject.Inject

class SingleCountryDetailsDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun fetchSingleCountryDetails(countryName: String
    ): Result<List<SingleCountryDetailsDataModel>, Error> = safeCall(
        execute = {
            apiService.fetchSingleCountry(countryName = countryName)
        },
    )
}