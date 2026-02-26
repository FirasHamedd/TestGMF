package com.example.testgmf.presentation.mapper

import com.example.testgmf.domain.model.CountryDomainModel
import com.example.testgmf.presentation.state.CountryUIState
import javax.inject.Inject

class AllCountriesUIMapper @Inject constructor() {

    fun mapDomainToUI(
        allCountriesDomainModel: List<CountryDomainModel>,
    ): List<CountryUIState> = allCountriesDomainModel.map { countryDomainModel ->
        CountryUIState(
            countryFlag = countryDomainModel.countryFlag,
            countryName = countryDomainModel.countryName,
        )
    }
}