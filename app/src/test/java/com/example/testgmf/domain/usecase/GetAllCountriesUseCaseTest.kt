package com.example.testgmf.domain.usecase

import com.example.testgmf.domain.model.CountryDomainModel
import com.example.testgmf.domain.repository.AllCountriesRepository
import com.example.testgmf.shared.domain.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class GetAllCountriesUseCaseTest {

    private val allCountriesRepository: AllCountriesRepository = mockk()
    private lateinit var getAllCountriesUseCase: GetAllCountriesUseCase

    @BeforeEach
    fun setUp() {
        getAllCountriesUseCase = GetAllCountriesUseCase(
            allCountriesRepository = allCountriesRepository,
        )
    }

    @Test
    fun `Given repository returns list of countries - When getAllCountries is called - Then should return sorted countries list`() =
        runTest {
            // Given
            val france = CountryDomainModel(
                countryFlag = "countryFlag",
                countryName = "France"
            )
            val tunisia = CountryDomainModel(
                countryFlag = "countryFlag",
                countryName = "Tunisia"
            )
            val givenCountriesList = listOf(
                tunisia,
                france,
            )
            val expectedCountriesList = listOf(
                france,
                tunisia,
            )
            coEvery {
                allCountriesRepository.getAllCountries()
            } returns Result.Success(value = givenCountriesList)

            // When
            val actualResult = getAllCountriesUseCase.getAllCountries()

            // Then
            assertTrue(actual = actualResult is Result.Success)
            assertEquals(expected = expectedCountriesList, actual = actualResult.value)
            coVerify(exactly = 1) {
                allCountriesRepository.getAllCountries()
            }
        }
}