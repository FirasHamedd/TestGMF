package com.example.testgmf.presentation.mapper

import com.example.testgmf.domain.model.CountryDomainModel
import com.example.testgmf.presentation.state.CountryUIState
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class AllCountriesUIMapperTest {

    private val allCountriesUIMapper = AllCountriesUIMapper()

    @Test
    fun `Given allCountriesDomainModel - When mapDomainToUI is called - Then should return mapped ui state`() {
        // Given
        val givenCountriesList = listOf(
            CountryDomainModel(
                countryFlag = "countryFlag",
                countryName = "countryName"
            )
        )

        // When
        val result = allCountriesUIMapper.mapDomainToUI(
            allCountriesDomainModel = givenCountriesList,
        )

        // Then
        assertEquals(
            expected = listOf(
                CountryUIState(
                    countryFlag = "countryFlag",
                    countryName = "countryName"
                )
            ),
            actual = result,
        )
    }
}