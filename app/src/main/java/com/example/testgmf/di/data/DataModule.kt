package com.example.testgmf.di.data

import com.example.testgmf.data.datasource.AllCountriesDataSource
import com.example.testgmf.data.datasource.SingleCountryDetailsDataSource
import com.example.testgmf.data.mapper.AllCountriesDomainModelMapper
import com.example.testgmf.data.mapper.SingleCountryDetailsDomainMapper
import com.example.testgmf.data.repository.AllCountriesRepositoryImpl
import com.example.testgmf.data.repository.SingleCountryDetailsRepositoryImpl
import com.example.testgmf.domain.repository.AllCountriesRepository
import com.example.testgmf.domain.repository.SingleCountryDetailsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideAllCountriesRepository(
        allCountriesDataSource: AllCountriesDataSource,
        allCountriesDomainModelMapper: AllCountriesDomainModelMapper,
    ): AllCountriesRepository {
        return AllCountriesRepositoryImpl(
            allCountriesDataSource = allCountriesDataSource,
            allCountriesDomainModelMapper = allCountriesDomainModelMapper,
        )
    }

    @Provides
    fun provideSingleCountryDetailsRepository(
        singleCountryDetailsDataSource: SingleCountryDetailsDataSource,
        singleCountryDetailsDomainMapper: SingleCountryDetailsDomainMapper,
    ): SingleCountryDetailsRepository {
        return SingleCountryDetailsRepositoryImpl(
            singleCountryDetailsDataSource = singleCountryDetailsDataSource,
            singleCountryDetailsDomainMapper = singleCountryDetailsDomainMapper,
        )
    }
}