package ru.kanogor.rickandmortypedia.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.kanogor.rickandmortypedia.data.SearchRickAndMorty
import ru.kanogor.rickandmortypedia.data.dto.characters.mapToListDomain
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData

class CharactersPagingSource(
    private val api: SearchRickAndMorty,
) :
    PagingSource<Int, CharacterData>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterData>): Int = FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterData> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            api.getCharacters(page)
        }.fold(
            onSuccess = {
                val characterList = it.body()?.results?.mapToListDomain() ?: emptyList()
                LoadResult.Page(
                    data = characterList,
                    prevKey = null,
                    nextKey = if (characterList.isEmpty()) null else {
                        page + 1
                    },
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}