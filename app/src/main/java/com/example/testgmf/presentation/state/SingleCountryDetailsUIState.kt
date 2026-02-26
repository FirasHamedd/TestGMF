package com.example.testgmf.presentation.state

data class SingleCountryDetailsUIState(
    val countryDetails: CountryDetails = CountryDetails(),
    val isLoading: Boolean = false,
)

data class CountryDetails(
    val capital: String = String(),
    val continent: String = String(),
    val flag: String = String(),
    val independent: Boolean = false,
    val landlocked: Boolean = false,
    val name: String = String(),
    val population: Int = 0,
)

