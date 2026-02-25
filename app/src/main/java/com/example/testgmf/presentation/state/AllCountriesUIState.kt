package com.example.testgmf.presentation.state

data class AllCountriesUIState(
    val countries: List<CountryUIState> = emptyList(),
    val isLoading: Boolean = false,
)
data class CountryUIState(
    val countryFlag: String,
    val countryName: String,
)
