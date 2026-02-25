package com.example.testgmf.shared.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.testgmf.presentation.view.AllCountriesScreenRoot
import com.example.testgmf.presentation.view.SingleCountryScreenRoot

fun EntryProviderScope<NavKey>.allCountriesGraph(navigator: Navigator) {
    entry<Routes.AllCountries> {
        AllCountriesScreenRoot(
            onNavigateToDetails = { countryName ->
                navigator.navigate(Routes.SingleCountry(countryName = countryName))
            },
        )
    }
}

fun EntryProviderScope<NavKey>.singleCountryGraph(navigator: Navigator) {
    entry<Routes.SingleCountry> {
        SingleCountryScreenRoot(
            countryName = it.countryName,
            onBackClicked = { navigator.goBack() },
        )
    }
}