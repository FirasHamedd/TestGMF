package com.example.testgmf.presentation.viewmodel.details

sealed interface SingleCountryDetailsViewModelEvents {
    data object ShowErrorDialog : SingleCountryDetailsViewModelEvents
}