package com.example.testgmf.data.mapper

import com.example.testgmf.data.model.CountryDataModel
import com.example.testgmf.data.model.Flags
import com.example.testgmf.data.model.Name
import com.example.testgmf.domain.model.CountryDomainModel
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AllCountriesDomainModelMapperTest {

    private val allCountriesDomainModelMapper = AllCountriesDomainModelMapper()

    @Test
    fun `Given countries data list - When mapToDomainModel is called - Then should return mapped countries domain list`() {
        // Given
        val givenCountriesList = listOf(
            CountryDataModel(
                name = Name(
                    common = "common",
                    official = "official",
                ),
                flags = Flags(
                    png = "png",
                    svg = "svg",
                    alt = "alt"
                )
            )
        )
        val expectedCountriesList = listOf(
            CountryDomainModel(
                countryFlag = "png",
                countryName = "official",
            )
        )

        // When
        val actualResult = allCountriesDomainModelMapper.mapToDomainModel(
            allCountriesDataModel = givenCountriesList,
        )

        // Then
        assertEquals(
            expected = expectedCountriesList.first().countryFlag,
            actual = actualResult.first().countryFlag
        )
        assertEquals(
            expected = expectedCountriesList.first().countryName,
            actual = actualResult.first().countryName
        )
    }
}