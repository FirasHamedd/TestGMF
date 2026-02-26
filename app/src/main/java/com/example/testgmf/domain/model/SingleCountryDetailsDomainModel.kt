package com.example.testgmf.domain.model

data class SingleCountryDetailsDomainModel(
    val capital: String,
    val continent: String,
    val flag: String,
    val independent: Boolean,
    val landlocked: Boolean,
    val name: String,
    val population: Int,
)