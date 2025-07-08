package ru.kanogor.rickandmortypedia.presentation.singleCharacter

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.kanogor.rickandmortypedia.common.Entity
import ru.kanogor.rickandmortypedia.domain.GetSingleCharacterUseCase
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData

class SingleCharacterViewModel(
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase,
) : ViewModel() {

    private val _singleCharacter = MutableStateFlow<CharacterData?>(null)
    val singleCharacter: StateFlow<CharacterData?> = _singleCharacter

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    suspend fun getSingleCharacter(id: Int) {
        _isLoading.value = true
        when (val character = getSingleCharacterUseCase.execute(id)) {
            is Entity.Error -> {
                Log.d("ZZZZ", "getSingleCharacter Error = ${character.message}")
                // TODO error alert
            }

            is Entity.Success -> {
                _singleCharacter.update { character.data }
            }
        }
        _isLoading.value = false
    }

}