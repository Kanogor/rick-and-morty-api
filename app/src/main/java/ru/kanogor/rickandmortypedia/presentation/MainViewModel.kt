package ru.kanogor.rickandmortypedia.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase
import ru.kanogor.rickandmortypedia.entity.Results
import ru.kanogor.rickandmortypedia.presentation.recyclerview.CharactersPagingSource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val getRickAndMortyCharactersUseCase: GetRickAndMortyCharactersUseCase) :
    ViewModel() {

    var pagedCharacters: Flow<PagingData<Results>> = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = {
            CharactersPagingSource(getRickAndMortyCharactersUseCase)
        }
    ).flow
}