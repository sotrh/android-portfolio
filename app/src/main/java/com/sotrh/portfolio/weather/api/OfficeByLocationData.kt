package com.sotrh.portfolio.weather.api

import com.google.gson.annotations.SerializedName

data class OfficeByLocationData (
    val id: String,
    val properties: OfficeProperties
)

data class OfficeProperties (
    @SerializedName("@id")
    val id: String,
    val cwa: String,
    val gridX: Int,
    val gridY: Int,
    val relativeLocation: RelativeLocation
)

data class RelativeLocation(
    val properties: LocationProperties
)

data class LocationProperties(
    val city: String,
    val state: String
)
