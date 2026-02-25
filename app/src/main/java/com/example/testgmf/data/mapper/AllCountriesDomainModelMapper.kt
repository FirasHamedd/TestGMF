package com.example.testgmf.data.mapper

import com.example.testgmf.data.model.CountryDataModel
import com.example.testgmf.domain.model.CountryDomainModel
import javax.inject.Inject

class AllCountriesDomainModelMapper @Inject constructor() {

    fun mapToDomainModel(
        allCountriesDataModel: List<CountryDataModel>,
    ): List<CountryDomainModel> {
        return allCountriesDataModel.map {
            CountryDomainModel(
                countryFlag = it.flags.png,
                countryName = it.name.official,
            )
        }
    }
}