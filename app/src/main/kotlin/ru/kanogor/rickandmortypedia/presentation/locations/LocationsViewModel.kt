package ru.kanogor.rickandmortypedia.presentation.locations

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyLocationsUseCase
import ru.kanogor.rickandmortypedia.domain.entity.LocationData

class LocationsViewModel(
    private val getRickAndMortyLocationsUseCase: GetRickAndMortyLocationsUseCase,
) : ViewModel() {

    fun pagedLocations(): Flow<PagingData<LocationData>> {
        return getRickAndMortyLocationsUseCase.execute()
    }

}