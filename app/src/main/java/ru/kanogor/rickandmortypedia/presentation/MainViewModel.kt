package ru.kanogor.rickandmortypedia.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase
import ru.kanogor.rickandmortypedia.entity.Results
import ru.kanogor.rickandmortypedia.presentation.recyclerview.CharactersPagingSource

class MainViewModel(
    private val getRickAndMortyCharactersUseCase: GetRickAndMortyCharactersUseCase
) : ViewModel() {

    var pagedCharacters: Flow<PagingData<Results>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            CharactersPagingSource(getRickAndMortyCharactersUseCase)
        }
    ).flow

    companion object {
        private const val PAGE_SIZE = 20
    }
}