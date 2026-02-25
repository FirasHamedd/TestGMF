package com.example.testgmf.domain.repository

import com.example.testgmf.domain.model.CountryDomainModel
import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result

interface AllCountriesRepository {
    suspend fun getAllCountries(): Result<List<CountryDomainModel>, Error>
}