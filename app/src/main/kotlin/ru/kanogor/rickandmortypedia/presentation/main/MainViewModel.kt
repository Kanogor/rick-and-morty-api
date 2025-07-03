package ru.kanogor.rickandmortypedia.presentation.main

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
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