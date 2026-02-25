package com.example.testgmf.presentation.viewmodel

sealed interface AllCountriesViewModelEvents {
    data object ShowErrorDialog : AllCountriesViewModelEvents
    data class NavigateToCountryDetails(val countryName: String) : AllCountriesViewModelEvents
}