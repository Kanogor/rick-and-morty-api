package ru.kanogor.rickandmortypedia.domain.entity

data class Locations(
    val info: InfoLocation,
    val result: List<LocationData>
)

data class InfoLocation(
    val count: Int,
    val pages: Int
)

data class LocationData(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)
