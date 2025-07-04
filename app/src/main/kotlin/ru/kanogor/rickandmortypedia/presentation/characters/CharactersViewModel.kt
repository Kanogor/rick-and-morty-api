package ru.kanogor.rickandmortypedia.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyLocationsUseCase
import ru.kanogor.rickandmortypedia.domain.GetSingleCharacterUseCase
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.LocationData

class CharactersViewModel(
    private val getRickAndMortyCharactersUseCase: GetRickAndMortyCharactersUseCase
) : ViewModel() {

    fun pagedCharacters(): Flow<PagingData<CharacterData>> {
        return getRickAndMortyCharactersUseCase.execute()
    }

}