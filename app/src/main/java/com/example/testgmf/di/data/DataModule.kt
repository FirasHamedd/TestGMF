package com.example.testgmf.di.data

import com.example.testgmf.data.datasource.AllCountriesDataSource
import com.example.testgmf.data.mapper.AllCountriesDomainModelMapper
import com.example.testgmf.data.repository.AllCountriesRepositoryImpl
import com.example.testgmf.domain.repository.AllCountriesRepository
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
}