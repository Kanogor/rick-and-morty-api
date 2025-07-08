package ru.kanogor.rickandmortypedia.presentation.singleCharacter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.kanogor.rickandmortypedia.common.Entity
import ru.kanogor.rickandmortypedia.domain.GetEpisodesUseCase
import ru.kanogor.rickandmortypedia.domain.GetSingleCharacterUseCase
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.Episode

class SingleCharacterViewModel(
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase,
) : ViewModel() {

    private val _singleCharacter = MutableStateFlow<CharacterData?>(null)
    val singleCharacter: StateFlow<CharacterData?> = _singleCharacter

    private val _isCharacterLoading = MutableStateFlow(false)
    val isCharacterLoading: StateFlow<Boolean> = _isCharacterLoading

    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes

    private val _isEpisodesLoading = MutableStateFlow(false)
    val isEpisodesLoading: StateFlow<Boolean> = _isEpisodesLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private fun showErrorMessage(message: String) {
        _errorMessage.update { message }
    }

    private fun hideErrorMessage() {
        _errorMessage.update { null }
    }

    suspend fun getSingleCharacter(id: Int) {
        hideErrorMessage()
        _isCharacterLoading.value = true
        when (val character = getSingleCharacterUseCase.execute(id)) {
            is Entity.Error -> {
                showErrorMessage(message = character.message)
                _isCharacterLoading.value = false
            }

            is Entity.Success -> {
                _singleCharacter.update { character.data }
                getEpisodes(episodesUrlList = character.data.episode)
                _isCharacterLoading.value = false
            }
        }
    }

    private suspend fun getEpisodes(episodesUrlList: List<String>) {
        _isEpisodesLoading.value = true
        when (val result = getEpisodesUseCase.execute(episodesUrlList)) {
            is Entity.Error -> {
                showErrorMessage(message = result.message)
                _isEpisodesLoading.value = false
            }

            is Entity.Success -> {
                _episodes.update { result.data }
                _isEpisodesLoading.value = false
            }
        }
    }

}