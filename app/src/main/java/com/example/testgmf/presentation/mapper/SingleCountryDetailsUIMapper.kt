package com.example.testgmf.presentation.mapper

import com.example.testgmf.domain.model.SingleCountryDetailsDomainModel
import com.example.testgmf.presentation.state.CountryDetails
import com.example.testgmf.presentation.state.SingleCountryDetailsUIState
import javax.inject.Inject

class SingleCountryDetailsUIMapper @Inject constructor() {

    fun mapDomainToUI(
        singleCountryDetailsDomainModel: SingleCountryDetailsDomainModel,
    ): SingleCountryDetailsUIState = SingleCountryDetailsUIState(
        countryDetails = CountryDetails(
            capital = singleCountryDetailsDomainModel.capital,
            continent = singleCountryDetailsDomainModel.continent,
            flag = singleCountryDetailsDomainModel.flag,
            independent = singleCountryDetailsDomainModel.independent,
            landlocked = singleCountryDetailsDomainModel.landlocked,
            name = singleCountryDetailsDomainModel.name,
            population = singleCountryDetailsDomainModel.population,
        ),
        isLoading = false,
    )
}