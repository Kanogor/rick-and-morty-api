package ru.kanogor.rickandmortypedia.domain

import kotlinx.coroutines.delay
import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData

class GetRickAndMortyCharactersUseCase(private val repository: RickAndMortyRepository) {

    suspend fun execute(page: Int): List<CharacterData> {
        delay(2000)  // для более плавной загрузки страниц
        return repository.getCharacters(page).results
    }
}