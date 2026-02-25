package com.example.testgmf.data.datasource

import com.example.testgmf.data.model.CountryDataModel
import com.example.testgmf.shared.data.ApiService
import com.example.testgmf.shared.data.utils.safeCall
import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result
import javax.inject.Inject

class AllCountriesDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun fetchAllCountries(): Result<List<CountryDataModel>, Error> = safeCall(
        execute = {
            apiService.fetchAllCountries()
        },
    )
}
