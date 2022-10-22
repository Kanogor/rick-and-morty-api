package ru.kanogor.rickandmortypedia.data

import ru.kanogor.rickandmortypedia.entity.RickAndMortyCharacters

class RickAndMortyRepository(private val searchRickAndMorty: SearchRickAndMorty) {

    suspend fun getCharacters(page: Int): RickAndMortyCharacters {
        val getApi = searchRickAndMorty.getCharacters(page)
        return getApi.body()!!
    }
}