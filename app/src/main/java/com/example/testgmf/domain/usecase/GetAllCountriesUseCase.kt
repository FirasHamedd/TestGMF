package com.example.testgmf.domain.usecase

import com.example.testgmf.domain.repository.AllCountriesRepository
import com.example.testgmf.shared.domain.map
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val allCountriesRepository: AllCountriesRepository,
) {
    suspend fun getAllCountries() =
        allCountriesRepository.getAllCountries().map { countryDomainModels ->
            countryDomainModels.sortedBy { it.countryName }
        }
}