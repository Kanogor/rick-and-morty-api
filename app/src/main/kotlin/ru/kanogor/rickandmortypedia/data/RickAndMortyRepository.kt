package ru.kanogor.rickandmortypedia.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.common.BaseRepository
import ru.kanogor.rickandmortypedia.common.Entity
import ru.kanogor.rickandmortypedia.common.ResponseStatus
import ru.kanogor.rickandmortypedia.data.dto.characters.mapToDomain
import ru.kanogor.rickandmortypedia.data.dto.episodes.mapToDomainList
import ru.kanogor.rickandmortypedia.data.pagingsource.CharactersPagingSource
import ru.kanogor.rickandmortypedia.data.pagingsource.LocationsPagingSource
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.Episode
import ru.kanogor.rickandmortypedia.domain.entity.LocationData

class RickAndMortyRepository(private val searchRickAndMorty: SearchRickAndMorty) :
    BaseRepository() {

    fun getCharacters(): Flow<PagingData<CharacterData>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            CharactersPagingSource(api = searchRickAndMorty)
        }
    ).flow

    fun getLocations(): Flow<PagingData<LocationData>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            LocationsPagingSource(api = searchRickAndMorty)
        }
    ).flow

    suspend fun getSingleCharacter(id: Int): Entity<CharacterData> {
        val response = saveApiSuspendResult {
            searchRickAndMorty.getSingleCharacter(id)
        }

        return when (response) {
            is ResponseStatus.Error -> {
                response.asEntity(error = response.exception.message ?: BASE_ERROR_MESSAGE)
            }

            is ResponseStatus.Success -> {
                response.asEntity(data = response.data?.mapToDomain())
            }
        }
    }

    suspend fun getEpisodesList(episodesUrlList: List<String>): Entity<List<Episode>> {
        val ids = episodesUrlList.map { it.substringAfterLast("/") }
        val response = saveApiSuspendResult {
            searchRickAndMorty.getEpisodesById(ids)
        }

        return when (response) {
            is ResponseStatus.Error -> {
                response.asEntity(error = response.exception.message ?: BASE_ERROR_MESSAGE)
            }

            is ResponseStatus.Success -> {
                response.asEntity(data = response.data?.mapToDomainList())
            }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}