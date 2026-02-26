package com.example.testgmf.data.repository

import com.example.testgmf.data.datasource.SingleCountryDetailsDataSource
import com.example.testgmf.data.mapper.SingleCountryDetailsDomainMapper
import com.example.testgmf.domain.model.SingleCountryDetailsDomainModel
import com.example.testgmf.domain.repository.SingleCountryDetailsRepository
import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result
import com.example.testgmf.shared.domain.map
import javax.inject.Inject

class SingleCountryDetailsRepositoryImpl @Inject constructor(
    private val singleCountryDetailsDataSource: SingleCountryDetailsDataSource,
    private val singleCountryDetailsDomainMapper: SingleCountryDetailsDomainMapper,
): SingleCountryDetailsRepository {

    override suspend fun getSingleCountryDetails(
        countryName: String,
    ): Result<SingleCountryDetailsDomainModel, Error> {
        val result = singleCountryDetailsDataSource.fetchSingleCountryDetails(
            countryName = countryName,
        )
        return result.map { countryDetails ->
            singleCountryDetailsDomainMapper.mapToDomainModel(
                singleCountryDetailsDataModel = countryDetails,
            )
        }
    }
}