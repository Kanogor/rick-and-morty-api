package ru.kanogor.rickandmortypedia.data

import ru.kanogor.rickandmortypedia.entity.RickAndMortyCharacters

class RickAndMortyRepository(private val searchRickAndMorty: SearchRickAndMorty) {

    suspend fun getCharacters(): RickAndMortyCharacters {
        val getApi = searchRickAndMorty.getCharacters()
        return getApi.body()!!
    }
}