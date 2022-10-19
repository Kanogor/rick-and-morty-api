package ru.kanogor.rickandmortypedia.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.kanogor.rickandmortypedia.entity.*
import ru.kanogor.rickandmortypedia.entity.Info
import ru.kanogor.rickandmortypedia.entity.Location
import ru.kanogor.rickandmortypedia.entity.Origin
import ru.kanogor.rickandmortypedia.entity.Results

@JsonClass(generateAdapter = true)
data class RickAndMortyCharactersDto(
    @Json(name = "info") override val info: ru.kanogor.rickandmortypedia.data.Info,
    @Json(name = "results") override val results: List<ru.kanogor.rickandmortypedia.data.Results>
) : RickAndMortyCharacters

@JsonClass(generateAdapter = true)
data class Info(
    @Json(name = "count") override val count: Int,
    @Json(name = "pages") override val pages: Int,
    @Json(name = "next") override val next: String?,
    @Json(name = "prev") override val prev: String?
) : Info

@JsonClass(generateAdapter = true)
data class Results(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String,
    @Json(name = "status") override val status: String,
    @Json(name = "species") override val species: String,
    @Json(name = "type") override val type: String,
    @Json(name = "gender") override val gender: String,
    @Json(name = "origin") override val origin: ru.kanogor.rickandmortypedia.data.Origin,
    @Json(name = "location") override val location: ru.kanogor.rickandmortypedia.data.Location,
    @Json(name = "image") override val image: String,
    @Json(name = "episode") override val episode: List<String>,
    @Json(name = "url") override val url: String,
    @Json(name = "created") override val created: String
) : Results

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "name") override val name: String,
    @Json(name = "url") override val url: String
) : Location

@JsonClass(generateAdapter = true)
data class Origin(
    @Json(name = "name") override val name: String,
    @Json(name = "url") override val url: String
) : Origin