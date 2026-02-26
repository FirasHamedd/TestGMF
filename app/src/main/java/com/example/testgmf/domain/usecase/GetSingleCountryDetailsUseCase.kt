package com.example.testgmf.domain.usecase

import com.example.testgmf.domain.repository.SingleCountryDetailsRepository
import javax.inject.Inject

class GetSingleCountryDetailsUseCase @Inject constructor(
    private val singleCountryDetailsRepository: SingleCountryDetailsRepository,
) {
    suspend fun getSingleCountryDetails(countryName: String) =
        singleCountryDetailsRepository.getSingleCountryDetails(countryName = countryName)
}