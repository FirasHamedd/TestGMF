package com.example.testgmf.domain.repository

import com.example.testgmf.domain.model.SingleCountryDetailsDomainModel
import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result

interface SingleCountryDetailsRepository {

    suspend fun getSingleCountryDetails(
        countryName: String,
    ): Result<SingleCountryDetailsDomainModel, Error>
}