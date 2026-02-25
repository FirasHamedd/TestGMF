package com.example.testgmf.shared.presentation.navigation

import androidx.annotation.Keep
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Keep
sealed interface Routes : NavKey {
    @Keep
    @Serializable
    data object AllCountries : Routes

    @Keep
    @Serializable
    data class SingleCountry(val countryName: String) : Routes
}
