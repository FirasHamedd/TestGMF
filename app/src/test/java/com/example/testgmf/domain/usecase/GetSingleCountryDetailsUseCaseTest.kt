package com.example.testgmf.domain.usecase

import com.example.testgmf.domain.model.SingleCountryDetailsDomainModel
import com.example.testgmf.domain.repository.SingleCountryDetailsRepository
import com.example.testgmf.shared.domain.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetSingleCountryDetailsUseCaseTest {

    private val singleCountryDetailsRepository: SingleCountryDetailsRepository = mockk()
    private lateinit var getSingleCountryDetailsUseCase: GetSingleCountryDetailsUseCase

    @BeforeEach
    fun setUp() {
        getSingleCountryDetailsUseCase = GetSingleCountryDetailsUseCase(
            singleCountryDetailsRepository = singleCountryDetailsRepository,
        )
    }

    @Test
    fun `Given repository returns country details - When getSingleCountryDetails is called - Then should return country details`() =
        runTest {
            // Given
            val countryDetailsDomainModel = SingleCountryDetailsDomainModel(
                capital = "capital",
                continent = "continent",
                flag = "flag",
                independent = true,
                landlocked = true,
                name = "name",
                population = 1000
            )
            coEvery {
                singleCountryDetailsRepository.getSingleCountryDetails(countryName = any())
            } returns Result.Success(value = countryDetailsDomainModel)

            // When
            val actualResult = getSingleCountryDetailsUseCase.getSingleCountryDetails(
                countryName = "countryName"
            )

            // Then
            assertTrue(actual = actualResult is Result.Success)
            assertEquals(expected = countryDetailsDomainModel, actual = actualResult.value)
            coVerify(exactly = 1) {
                singleCountryDetailsRepository.getSingleCountryDetails(countryName = "countryName")
            }
        }
}