package com.example.testgmf.data.repository

import com.example.testgmf.data.datasource.AllCountriesDataSource
import com.example.testgmf.data.mapper.AllCountriesDomainModelMapper
import com.example.testgmf.data.model.CountryDataModel
import com.example.testgmf.domain.model.CountryDomainModel
import com.example.testgmf.shared.domain.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class AllCountriesRepositoryImplTest {
    private val allCountriesDataSource: AllCountriesDataSource = mockk()
    private val allCountriesDomainModelMapper: AllCountriesDomainModelMapper = mockk()
    private lateinit var allCountriesRepositoryImpl: AllCountriesRepositoryImpl

    @BeforeEach
    fun setUp() {
        allCountriesRepositoryImpl = AllCountriesRepositoryImpl(
            allCountriesDataSource = allCountriesDataSource,
            allCountriesDomainModelMapper = allCountriesDomainModelMapper,
        )
    }

    @Test
    fun `Given data source returns success - When getAllCountries is called - Then should return success with list of all countries`() =
        runTest {
            // Given
            val givenCountryDataModel: CountryDataModel = mockk()
            val expectedCountryDomainModel: CountryDomainModel = mockk()
            coEvery {
                allCountriesDataSource.fetchAllCountries()
            } returns Result.Success(value = listOf(givenCountryDataModel))
            every {
                allCountriesDomainModelMapper.mapToDomainModel(allCountriesDataModel = any())
            } returns listOf(expectedCountryDomainModel)

            // When
            val actualResult = allCountriesRepositoryImpl.getAllCountries()

            // Then
            assertTrue(actualResult is Result.Success)
            assertEquals(
                expected = listOf(expectedCountryDomainModel),
                actual = actualResult.value,
            )
            coVerify(exactly = 1) {
                allCountriesDataSource.fetchAllCountries()
                allCountriesDomainModelMapper.mapToDomainModel(
                    allCountriesDataModel = listOf(givenCountryDataModel)
                )
            }
        }

}