package ru.kanogor.rickandmortypedia.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.kanogor.rickandmortypedia.data.SearchRickAndMorty
import ru.kanogor.rickandmortypedia.domain.entity.LocationData

class LocationsPagingSource(
    private val api: SearchRickAndMorty,
) :
    PagingSource<Int, LocationData>() {
    override fun getRefreshKey(state: PagingState<Int, LocationData>): Int = FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationData> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            api.getLocation(page)
        }.fold(
            onSuccess = { result ->
                val locationsList = result.body()?.result ?: emptyList()
                LoadResult.Page(
                    data = locationsList,
                    prevKey = null,
                    nextKey = if (locationsList.isEmpty()) null else {
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