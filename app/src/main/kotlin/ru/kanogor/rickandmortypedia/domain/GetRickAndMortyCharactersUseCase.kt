package ru.kanogor.rickandmortypedia.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData

class GetRickAndMortyCharactersUseCase(private val repository: RickAndMortyRepository) {

    fun execute(): Flow<PagingData<CharacterData>> {
        return repository.getCharacters()
    }
}