package ru.kanogor.rickandmortypedia.data.dto.locations


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationsDto(
    @Json(name = "info")  val info: InfoLocationDto,
    @Json(name = "results")  val result: List<LocationDataDto>
)

@JsonClass(generateAdapter = true)
data class InfoLocationDto(
    @Json(name = "count")  val count: Int,
    @Json(name = "pages")  val pages: Int
)

@JsonClass(generateAdapter = true)
data class LocationDataDto(
    @Json(name = "id")  val id: Int,
    @Json(name = "name")  val name: String,
    @Json(name = "type")  val type: String,
    @Json(name = "dimension")  val dimension: String
)