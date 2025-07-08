package ru.kanogor.rickandmortypedia.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData

class CharactersViewModel(
    private val getRickAndMortyCharactersUseCase: GetRickAndMortyCharactersUseCase
) : ViewModel() {

    private val _characters = MutableStateFlow(PagingData.empty<CharacterData>())
    val characters = _characters.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    suspend fun getPagedCharacters() {
        val characters = getRickAndMortyCharactersUseCase.execute().distinctUntilChanged()
            .cachedIn(viewModelScope).first()

        _characters.update {
            characters
        }
    }

    fun showErrorMessage(isError: Boolean) {
        _isError.update { isError }
    }

}