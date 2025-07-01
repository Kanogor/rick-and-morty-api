package ru.kanogor.rickandmortypedia.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kanogor.rickandmortypedia.data.dto.CharacterData
import ru.kanogor.rickandmortypedia.data.dto.RickAndMortyCharactersDto
import ru.kanogor.rickandmortypedia.data.dto.RickAndMortyLocationsDto

interface SearchRickAndMorty {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<RickAndMortyCharactersDto>

    @GET("location")
    suspend fun getLocation(
        @Query("page") page: Int
    ): Response<RickAndMortyLocationsDto>

    @GET("character/{id}")
    suspend fun getSingleCharacter(
        @Path("id") id: Int
    ): Response<CharacterData>

}