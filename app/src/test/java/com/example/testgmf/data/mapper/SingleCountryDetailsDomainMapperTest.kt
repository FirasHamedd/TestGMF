package com.example.testgmf.data.mapper

import com.example.testgmf.data.model.Flags
import com.example.testgmf.data.model.Name
import com.example.testgmf.data.model.SingleCountryDetailsDataModel
import com.example.testgmf.domain.model.SingleCountryDetailsDomainModel
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SingleCountryDetailsDomainMapperTest {

    private val singleCountryDetailsDomainMapper = SingleCountryDetailsDomainMapper()

    @Test
    fun `Given single country data model - When mapToDomainModel is called - Then should return mapped country domain model`() {
        // Given
        val givenCountry = SingleCountryDetailsDataModel(
            capital = listOf("capital"),
            continents = listOf("continent"),
            flags = Flags(
                png = "png",
                svg = "svg",
                alt = "alt",
            ),
            independent = true,
            landlocked = false,
            name = Name(
                common = "common",
                official = "official"
            ),
            population = 1000
        )
        val expectedCountry = SingleCountryDetailsDomainModel(
            capital = "capital",
            continent = "continent",
            flag = "png",
            independent = true,
            landlocked = false,
            name = "official",
            population = 1000
        )

        // When
        val actualResult = singleCountryDetailsDomainMapper.mapToDomainModel(
            singleCountryDetailsDataModel = listOf(givenCountry),
        )

        // Then
        assertEquals(
            expected = expectedCountry,
            actual = actualResult
        )
    }

    @Test
    fun `Given single country data model where continents and capitals list are empty - When mapToDomainModel is called - Then should return mapped country domain model`() {
        // Given
        val givenCountry = SingleCountryDetailsDataModel(
            capital = emptyList(),
            continents = emptyList(),
            flags = Flags(
                png = "png",
                svg = "svg",
                alt = "alt",
            ),
            independent = true,
            landlocked = false,
            name = Name(
                common = "common",
                official = "official"
            ),
            population = 1000
        )
        val expectedCountry = SingleCountryDetailsDomainModel(
            capital = "",
            continent = "",
            flag = "png",
            independent = true,
            landlocked = false,
            name = "official",
            population = 1000
        )

        // When
        val actualResult = singleCountryDetailsDomainMapper.mapToDomainModel(
            singleCountryDetailsDataModel = listOf(givenCountry),
        )

        // Then
        assertEquals(
            expected = expectedCountry,
            actual = actualResult
        )
    }
}