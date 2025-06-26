package ru.kanogor.rickandmortypedia.domain

import kotlinx.coroutines.delay
import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository

class GetRickAndMortyCharactersUseCase(private val repository: RickAndMortyRepository) {

    suspend fun execute(page: Int): List<ru.kanogor.rickandmortypedia.entity.CharacterData> {
        delay(2000)  // для более плавной загрузки страниц
        return repository.getCharacters(page).results
    }
}