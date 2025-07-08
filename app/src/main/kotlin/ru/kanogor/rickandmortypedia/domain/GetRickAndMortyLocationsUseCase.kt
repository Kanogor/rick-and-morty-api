package ru.kanogor.rickandmortypedia.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository
import ru.kanogor.rickandmortypedia.domain.entity.LocationData

class GetRickAndMortyLocationsUseCase(private val repository: RickAndMortyRepository) {

    fun execute(): Flow<PagingData<LocationData>> {
        return repository.getLocations()
    }
}