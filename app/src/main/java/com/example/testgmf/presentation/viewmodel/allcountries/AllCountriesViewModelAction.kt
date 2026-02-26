package com.example.testgmf.presentation.viewmodel.allcountries

sealed interface AllCountriesViewModelAction {
    data object OnAllCountriesScreenDisplayed : AllCountriesViewModelAction

    data class OnCountryClicked(val countryName: String) : AllCountriesViewModelAction

    data object OnErrorDialogRetryButtonClicked : AllCountriesViewModelAction
}