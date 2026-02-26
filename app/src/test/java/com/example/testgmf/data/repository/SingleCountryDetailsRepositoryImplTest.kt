package com.example.testgmf.data.repository

import com.example.testgmf.data.datasource.SingleCountryDetailsDataSource
import com.example.testgmf.data.mapper.SingleCountryDetailsDomainMapper
import com.example.testgmf.data.model.SingleCountryDetailsDataModel
import com.example.testgmf.domain.model.SingleCountryDetailsDomainModel
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

class SingleCountryDetailsRepositoryImplTest {

    private val singleCountryDetailsDataSource: SingleCountryDetailsDataSource = mockk()
    private val singleCountryDetailsDomainMapper: SingleCountryDetailsDomainMapper = mockk()
    private lateinit var singleCountryDetailsRepositoryImpl: SingleCountryDetailsRepositoryImpl

    @BeforeEach
    fun setUp() {
        singleCountryDetailsRepositoryImpl = SingleCountryDetailsRepositoryImpl(
            singleCountryDetailsDomainMapper = singleCountryDetailsDomainMapper,
            singleCountryDetailsDataSource = singleCountryDetailsDataSource,
        )
    }

    @Test
    fun `Given data source returns success - When getSingleCountryDetails is called - Then should return success`() =
        runTest {
            // Given
            val givenCountryDataModel: SingleCountryDetailsDataModel = mockk()
            val expectedCountryDomainModel: SingleCountryDetailsDomainModel = mockk()
            coEvery {
                singleCountryDetailsDataSource.fetchSingleCountryDetails(countryName = any())
            } returns Result.Success(value = listOf(givenCountryDataModel))
            every {
                singleCountryDetailsDomainMapper.mapToDomainModel(singleCountryDetailsDataModel = any())
            } returns expectedCountryDomainModel

            // When
            val actualResult = singleCountryDetailsRepositoryImpl.getSingleCountryDetails(
                countryName = "countryName",
            )

            // Then
            assertTrue(actualResult is Result.Success)
            assertEquals(
                expected = expectedCountryDomainModel,
                actual = actualResult.value,
            )
            coVerify(exactly = 1) {
                singleCountryDetailsDataSource.fetchSingleCountryDetails(countryName = "countryName")
                singleCountryDetailsDomainMapper.mapToDomainModel(
                    singleCountryDetailsDataModel = listOf(givenCountryDataModel)
                )
            }
        }
}