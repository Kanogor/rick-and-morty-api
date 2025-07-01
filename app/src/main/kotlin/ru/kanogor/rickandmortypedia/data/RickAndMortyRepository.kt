package ru.kanogor.rickandmortypedia.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.data.pagingsource.CharactersPagingSource
import ru.kanogor.rickandmortypedia.data.pagingsource.LocationsPagingSource
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.LocationData

class RickAndMortyRepository(private val searchRickAndMorty: SearchRickAndMorty) {

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

    companion object {
        private const val PAGE_SIZE = 20
    }

}