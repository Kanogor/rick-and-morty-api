package ru.kanogor.rickandmortypedia.domain

import kotlinx.coroutines.delay
import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository

class GetRickAndMortyCharactersUseCase(private val repository: RickAndMortyRepository) {

    suspend fun execute(page: Int): List<ru.kanogor.rickandmortypedia.entity.Results> {
        delay(2000)
        return repository.getCharacters(page).results
    }
}