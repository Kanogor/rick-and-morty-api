package ru.kanogor.rickandmortypedia.domain

import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository
import javax.inject.Inject

class GetRickAndMortyCharactersUseCase @Inject constructor(private val repository: RickAndMortyRepository) {

   suspend fun execute() : List<ru.kanogor.rickandmortypedia.entity.Results> {
        return repository.getCharacters().results
    }
}