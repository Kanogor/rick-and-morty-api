package ru.kanogor.rickandmortypedia.presentation.pagingsource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyLocationsUseCase
import ru.kanogor.rickandmortypedia.entity.LocationData

class LocationsPagingSource(
    private val getRickAndMortyLocationsUseCase: GetRickAndMortyLocationsUseCase,
    private val throwable: MutableLiveData<Throwable?>
) :
    PagingSource<Int, LocationData>() {
    override fun getRefreshKey(state: PagingState<Int, LocationData>): Int = FIRST_PAGE
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationData> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            getRickAndMortyLocationsUseCase.execute(page)
        }.fold(
            onSuccess = {
                throwable.value = null
                LoadResult.Page(
                    data = it,
                    prevKey = null,
                    nextKey = if (it.isEmpty()) null else {
                        page + 1
                    },
                )
            },
            onFailure = {
                throwable.value = it
                LoadResult.Error(it)
            }
        )
    }

    private companion object {
        private const val FIRST_PAGE = 1
    }
}