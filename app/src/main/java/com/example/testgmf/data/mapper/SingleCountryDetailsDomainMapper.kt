package com.example.testgmf.data.mapper

import com.example.testgmf.data.model.SingleCountryDetailsDataModel
import com.example.testgmf.domain.model.SingleCountryDetailsDomainModel
import javax.inject.Inject

class SingleCountryDetailsDomainMapper @Inject constructor() {

    fun mapToDomainModel(
        singleCountryDetailsDataModel: List<SingleCountryDetailsDataModel>,
    ): SingleCountryDetailsDomainModel {
        val country = singleCountryDetailsDataModel.first()
        return SingleCountryDetailsDomainModel(
            capital = country.capital?.firstOrNull() ?: String(),
            continent = country.continents.firstOrNull() ?: String(),
            flag = country.flags.png,
            independent = country.independent,
            landlocked = country.landlocked,
            name = country.name.official,
            population = country.population
        )
    }
}