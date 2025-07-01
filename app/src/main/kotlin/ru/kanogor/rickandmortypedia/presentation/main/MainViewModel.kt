package ru.kanogor.rickandmortypedia.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyLocationsUseCase
import ru.kanogor.rickandmortypedia.domain.GetSingleCharacterUseCase
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.LocationData

class MainViewModel(
    private val getRickAndMortyCharactersUseCase: GetRickAndMortyCharactersUseCase,
    private val getRickAndMortyLocationsUseCase: GetRickAndMortyLocationsUseCase,
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase,
) : ViewModel() {

    fun pagedCharacters(): Flow<PagingData<CharacterData>> {
        return getRickAndMortyCharactersUseCase.execute()
    }

    fun pagedLocations(): Flow<PagingData<LocationData>> {
        return getRickAndMortyLocationsUseCase.execute()
    }

    suspend fun getSingleCharacterFlow(id: Int): CharacterData? {
       return getSingleCharacterUseCase.execute(id)
    }

}