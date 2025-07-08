package ru.kanogor.rickandmortypedia.data.dto.characters

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharactersPagingDto(
    @Json(name = "info") val infoChar: InfoCharDto,
    @Json(name = "results") val results: List<CharacterDataDto>
)

@JsonClass(generateAdapter = true)
data class InfoCharDto(
    @Json(name = "count") val count: Int,
    @Json(name = "pages") val pages: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "prev") val prev: String?
)

@JsonClass(generateAdapter = true)
data class CharacterDataDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "status") val status: String,
    @Json(name = "species") val species: String,
    @Json(name = "type") val type: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "origin") val origin: OriginDto,
    @Json(name = "location") val locationCharData: LocationCharDataDto,
    @Json(name = "image") val image: String,
    @Json(name = "episode") val episode: List<String>,
    @Json(name = "url") val url: String,
    @Json(name = "created") val created: String
)

@JsonClass(generateAdapter = true)
data class LocationCharDataDto(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class OriginDto(
    @Json(name = "name")  val name: String,
    @Json(name = "url")  val url: String
)