package com.example.testgmf.data.repository

import com.example.testgmf.data.datasource.AllCountriesDataSource
import com.example.testgmf.data.mapper.AllCountriesDomainModelMapper
import com.example.testgmf.domain.model.CountryDomainModel
import com.example.testgmf.domain.repository.AllCountriesRepository
import com.example.testgmf.shared.domain.map
import javax.inject.Inject
import com.example.testgmf.shared.domain.Error
import com.example.testgmf.shared.domain.Result

class AllCountriesRepositoryImpl @Inject constructor(
    private val allCountriesDataSource: AllCountriesDataSource,
    private val allCountriesDomainModelMapper: AllCountriesDomainModelMapper,
): AllCountriesRepository {

    override suspend fun getAllCountries(): Result<List<CountryDomainModel>, Error> {
        val result = allCountriesDataSource.fetchAllCountries()
        return result.map { allCountriesDataModel ->
            allCountriesDomainModelMapper.mapToDomainModel(
                allCountriesDataModel = allCountriesDataModel,
            )
        }
    }
}
