package ru.kanogor.rickandmortypedia.data

import ru.kanogor.rickandmortypedia.domain.entity.RickAndMortyCharacters
import ru.kanogor.rickandmortypedia.domain.entity.RickAndMortyLocations

class RickAndMortyRepository(private val searchRickAndMorty: SearchRickAndMorty) {

    suspend fun getCharacters(page: Int): RickAndMortyCharacters {
        val getApi = searchRickAndMorty.getCharacters(page)
        return getApi.body()!!
    }

    suspend fun getLocations(page: Int): RickAndMortyLocations {
        val getApi = searchRickAndMorty.getLocation(page)
        return getApi.body()!!
    }

}