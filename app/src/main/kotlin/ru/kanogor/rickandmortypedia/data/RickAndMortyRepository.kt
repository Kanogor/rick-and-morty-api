package ru.kanogor.rickandmortypedia.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.common.BaseRepository
import ru.kanogor.rickandmortypedia.common.Entity
import ru.kanogor.rickandmortypedia.common.ResponseStatus
import ru.kanogor.rickandmortypedia.data.dto.characters.mapToDomain
import ru.kanogor.rickandmortypedia.data.pagingsource.CharactersPagingSource
import ru.kanogor.rickandmortypedia.data.pagingsource.LocationsPagingSource
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
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

    companion object {
        private const val PAGE_SIZE = 20
    }

}