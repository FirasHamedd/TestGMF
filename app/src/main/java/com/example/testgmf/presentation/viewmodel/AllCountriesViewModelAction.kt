package com.example.testgmf.presentation.viewmodel

sealed interface AllCountriesViewModelAction {
    data object OnAllCountriesScreenDisplayed : AllCountriesViewModelAction

    data class OnCountryClicked(val countryName: String) : AllCountriesViewModelAction
}