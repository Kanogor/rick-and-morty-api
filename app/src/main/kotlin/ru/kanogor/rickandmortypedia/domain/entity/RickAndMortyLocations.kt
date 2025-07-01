package ru.kanogor.rickandmortypedia.domain.entity

interface RickAndMortyLocations {
    val info: InfoLocation
    val result: List<LocationData>
}

interface InfoLocation {
    val count: Int
    val pages: Int
}

interface LocationData {
    val id: Int
    val name: String
    val type: String
    val dimension: String
}
