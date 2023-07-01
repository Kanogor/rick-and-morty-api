package ru.kanogor.rickandmortypedia.presentation.recyclerview

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase
import ru.kanogor.rickandmortypedia.entity.Results

class CharactersPagingSource(private val getRickAndMortyCharactersUseCase: GetRickAndMortyCharactersUseCase) :
    PagingSource<Int, Results>() {
    override fun getRefreshKey(state: PagingState<Int, Results>): Int = FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            getRickAndMortyCharactersUseCase.execute(page)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else {
                        page + 1
                    },
                )
            },
            onFailure = { LoadResult.Error(it) }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}