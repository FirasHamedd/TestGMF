package com.example.testgmf.presentation.viewmodel.allcountries

sealed interface AllCountriesViewModelEvents {
    data object ShowErrorDialog : AllCountriesViewModelEvents
    data class NavigateToCountryDetails(val countryName: String) : AllCountriesViewModelEvents
}