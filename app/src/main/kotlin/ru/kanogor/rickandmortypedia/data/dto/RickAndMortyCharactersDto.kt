package ru.kanogor.rickandmortypedia.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.kanogor.rickandmortypedia.domain.entity.InfoChar
import ru.kanogor.rickandmortypedia.domain.entity.LocationCharData
import ru.kanogor.rickandmortypedia.domain.entity.Origin
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.RickAndMortyCharacters

@JsonClass(generateAdapter = true)
data class RickAndMortyCharactersDto(
    @Json(name = "info") override val infoChar: ru.kanogor.rickandmortypedia.data.dto.InfoChar,
    @Json(name = "results") override val results: List<ru.kanogor.rickandmortypedia.data.dto.CharacterData>
) : RickAndMortyCharacters

@JsonClass(generateAdapter = true)
data class InfoChar(
    @Json(name = "count") override val count: Int,
    @Json(name = "pages") override val pages: Int,
    @Json(name = "next") override val next: String?,
    @Json(name = "prev") override val prev: String?
) : InfoChar

@JsonClass(generateAdapter = true)
data class CharacterData(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String,
    @Json(name = "status") override val status: String,
    @Json(name = "species") override val species: String,
    @Json(name = "type") override val type: String,
    @Json(name = "gender") override val gender: String,
    @Json(name = "origin") override val origin: ru.kanogor.rickandmortypedia.data.dto.Origin,
    @Json(name = "location") override val locationCharData: ru.kanogor.rickandmortypedia.data.dto.LocationCharData,
    @Json(name = "image") override val image: String,
    @Json(name = "episode") override val episode: List<String>,
    @Json(name = "url") override val url: String,
    @Json(name = "created") override val created: String
) : CharacterData

@JsonClass(generateAdapter = true)
data class LocationCharData(
    @Json(name = "name") override val name: String,
    @Json(name = "url") override val url: String
) : LocationCharData

@JsonClass(generateAdapter = true)
data class Origin(
    @Json(name = "name") override val name: String,
    @Json(name = "url") override val url: String
) : Origin