package com.example.testgmf.presentation.mapper

import com.example.testgmf.domain.model.SingleCountryDetailsDomainModel
import com.example.testgmf.presentation.state.CountryDetails
import com.example.testgmf.presentation.state.SingleCountryDetailsUIState
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SingleCountryDetailsUIMapperTest {

    private val singleCountryDetailsUIMapper = SingleCountryDetailsUIMapper()

    @Test
    fun `Given SingleCountryDetailsDomainModel - When mapDomainToUI is called - Then should return mapped ui state`() {
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

        // When
        val result = singleCountryDetailsUIMapper.mapDomainToUI(
            singleCountryDetailsDomainModel = countryDetailsDomainModel,
        )

        // Then
        assertEquals(
            expected = SingleCountryDetailsUIState(
                countryDetails = CountryDetails(
                    capital = "capital",
                    continent = "continent",
                    flag = "flag",
                    independent = true,
                    landlocked = true,
                    name = "name",
                    population = 1000
                ),
                isLoading = false,
            ),
            actual = result,
        )
    }
}