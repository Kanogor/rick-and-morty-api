package ru.kanogor.rickandmortypedia.domain

import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData

class GetSingleCharacterUseCase(private val repository: RickAndMortyRepository) {

    suspend fun execute(id: Int): CharacterData? {
        return repository.getSingleCharacter(id)
    }
}