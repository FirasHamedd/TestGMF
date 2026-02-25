package com.example.testgmf.presentation.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.testgmf.shared.presentation.navigation.Navigator
import com.example.testgmf.shared.presentation.navigation.Routes
import com.example.testgmf.shared.presentation.navigation.allCountriesGraph
import com.example.testgmf.shared.presentation.navigation.rememberNavigationState
import com.example.testgmf.shared.presentation.navigation.singleCountryGraph
import com.example.testgmf.shared.presentation.navigation.toEntries

@Composable
fun MainScreenRoot() {

    val navigationState = rememberNavigationState(
        startRoute = Routes.AllCountries,
        topLevelRoutes = setOf(Routes.AllCountries, Routes.SingleCountry(countryName = String())),
    )
    val navigator = remember { Navigator(state = navigationState) }
    val entryProvider = entryProvider {
        allCountriesGraph(navigator = navigator)
        singleCountryGraph(navigator = navigator)
    }

    NavDisplay(
        entries = navigator.state.toEntries(entryProvider),
        onBack = { navigator.goBack() },
        modifier = Modifier.fillMaxSize()
    )

}