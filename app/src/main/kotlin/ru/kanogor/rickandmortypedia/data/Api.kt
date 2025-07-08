package ru.kanogor.rickandmortypedia.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kanogor.rickandmortypedia.data.dto.characters.CharacterDataDto
import ru.kanogor.rickandmortypedia.data.dto.characters.CharactersPagingDto
import ru.kanogor.rickandmortypedia.data.dto.episodes.EpisodeDto
import ru.kanogor.rickandmortypedia.data.dto.locations.LocationsDto

interface SearchRickAndMorty {

    @GET("episode/{ids}")
    suspend fun getEpisodesById(
        @Path("ids") ids: List<String>
    ): Response<List<EpisodeDto>>

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<CharactersPagingDto>

    @GET("location")
    suspend fun getLocation(
        @Query("page") page: Int
    ): Response<LocationsDto>

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Response<CharacterDataDto>

}