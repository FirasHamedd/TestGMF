package com.example.testgmf.presentation.viewmodel.details

sealed interface SingleCountryDetailsViewModelAction {
    data class OnCountryDetailsScreenDisplayed(
        val countryName: String
    ) : SingleCountryDetailsViewModelAction
}