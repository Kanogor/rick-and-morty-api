package ru.kanogor.rickandmortypedia.presentation.singleCharacter

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    suspend fun getSingleCharacter(id: Int) {
        _isCharacterLoading.value = true
        when (val character = getSingleCharacterUseCase.execute(id)) {
            is Entity.Error -> {
                Log.d("ZZZZ", "getSingleCharacter Error = ${character.message}")
                // TODO error alert
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
                Log.d("ZZZZ", "getEpisodes Error = ${result.message}")
                // TODO error alert
                _isEpisodesLoading.value = false
            }

            is Entity.Success -> {
                _episodes.update { result.data }
                _isEpisodesLoading.value = false
            }
        }
    }

}