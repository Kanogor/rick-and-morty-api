package ru.kanogor.rickandmortypedia.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRickAndMorty {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): Response<RickAndMortyCharactersDto>

    @GET("location")
    suspend fun getLocation(
        @Query("page") page: Int
    ): Response<RickAndMortyLocationsDto>

}