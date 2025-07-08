package ru.kanogor.rickandmortypedia.presentation.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyLocationsUseCase
import ru.kanogor.rickandmortypedia.domain.entity.LocationData

class LocationsViewModel(
    private val getRickAndMortyLocationsUseCase: GetRickAndMortyLocationsUseCase,
) : ViewModel() {

    private val _locations = MutableStateFlow(PagingData.empty<LocationData>())
    val locations = _locations.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    suspend fun getPagedLocations() {
        val locations = getRickAndMortyLocationsUseCase.execute().distinctUntilChanged()
            .cachedIn(viewModelScope).first()

        _locations.update {
            locations
        }
    }

    fun showErrorMessage(isError: Boolean) {
        _isError.update { isError }
    }

}