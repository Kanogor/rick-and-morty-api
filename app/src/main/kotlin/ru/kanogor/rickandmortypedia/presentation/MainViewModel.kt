package ru.kanogor.rickandmortypedia.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyLocationsUseCase
import ru.kanogor.rickandmortypedia.entity.CharacterData
import ru.kanogor.rickandmortypedia.entity.LocationData
import ru.kanogor.rickandmortypedia.presentation.pagingsource.CharactersPagingSource
import ru.kanogor.rickandmortypedia.presentation.pagingsource.LocationsPagingSource

class MainViewModel(
    private val getRickAndMortyCharactersUseCase: GetRickAndMortyCharactersUseCase,
    private val getRickAndMortyLocationsUseCase: GetRickAndMortyLocationsUseCase
) : ViewModel() {

    private val throwableChar = MutableLiveData<Throwable?>(null)
    private val throwableLocation = MutableLiveData<Throwable?>(null)

    var pagedLocations: Flow<PagingData<LocationData>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            LocationsPagingSource(getRickAndMortyLocationsUseCase, throwableLocation)
        }
    ).flow.cachedIn(viewModelScope)

    var pagedCharacters: Flow<PagingData<CharacterData>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            CharactersPagingSource(getRickAndMortyCharactersUseCase, throwableChar)
        }
    ).flow.cachedIn(viewModelScope)

    companion object {
        private const val PAGE_SIZE = 20
    }
}