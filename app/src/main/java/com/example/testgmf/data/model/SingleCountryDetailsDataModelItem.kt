package com.example.testgmf.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class SingleCountryDetailsDataModel(
    @param:Json(name = "capital")
    val capital: List<String>?,
    @param:Json(name = "continents")
    val continents: List<String>,
    @param:Json(name = "flags")
    val flags: Flags,
    @param:Json(name = "independent")
    val independent: Boolean,
    @param:Json(name = "landlocked")
    val landlocked: Boolean,
    @param:Json(name = "name")
    val name: Name,
    @param:Json(name = "population")
    val population: Int,
)