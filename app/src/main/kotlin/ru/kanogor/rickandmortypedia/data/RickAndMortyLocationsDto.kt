package ru.kanogor.rickandmortypedia.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.kanogor.rickandmortypedia.domain.entity.RickAndMortyLocations

@JsonClass(generateAdapter = true)
data class RickAndMortyLocationsDto(
    @Json(name = "info") override val info: InfoLocation,
    @Json(name = "results") override val result: List<LocationData>
) : RickAndMortyLocations

@JsonClass(generateAdapter = true)
data class InfoLocation(
    @Json(name = "count") override val count: Int,
    @Json(name = "pages") override val pages: Int
) : ru.kanogor.rickandmortypedia.domain.entity.InfoLocation

@JsonClass(generateAdapter = true)
data class LocationData(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String,
    @Json(name = "type") override val type: String,
    @Json(name = "dimension") override val dimension: String
) : ru.kanogor.rickandmortypedia.domain.entity.LocationData