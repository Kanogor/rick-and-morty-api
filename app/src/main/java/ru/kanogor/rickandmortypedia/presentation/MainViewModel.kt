package ru.kanogor.rickandmortypedia.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(val getRickAndMortyCharactersUseCase: GetRickAndMortyCharactersUseCase) :
    ViewModel() {

    private val _charName = MutableStateFlow<String>("Click me")
    val charName = _charName.asStateFlow()

    init {
        reload()
    }

    fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getRickAndMortyCharactersUseCase.execute()
            }.fold(
                onSuccess = {
                    Log.d("ViewModel reaload", "all ok")
                    _charName.value = it[Random.nextInt(0,19)].name
                },
                onFailure = { Log.d("ViewModel reaload", it.message ?: "Что то пошло не так") }
            )
        }
    }
}