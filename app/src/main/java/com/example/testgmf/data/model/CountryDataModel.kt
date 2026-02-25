package com.example.testgmf.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class CountryDataModel(
    @param:Json(name = "flags")
    val flags: Flags,
    @param:Json(name = "name")
    val name: Name
)

@Keep
@JsonClass(generateAdapter = true)
data class Flags(
    @param:Json(name = "alt")
    val alt: String,
    @param:Json(name = "png")
    val png: String,
    @param:Json(name = "svg")
    val svg: String
)

@Keep
@JsonClass(generateAdapter = true)
data class Name(
    @param:Json(name = "common")
    val common: String,
    @param:Json(name = "official")
    val official: String
)
