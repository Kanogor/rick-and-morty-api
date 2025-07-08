package ru.kanogor.rickandmortypedia.domain

import ru.kanogor.rickandmortypedia.common.Entity
import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.Episode

class GetEpisodesUseCase(private val repository: RickAndMortyRepository) {

    suspend fun execute(episodesUrlList: List<String>): Entity<List<Episode>> {
        return repository.getEpisodesList(episodesUrlList)
    }
}