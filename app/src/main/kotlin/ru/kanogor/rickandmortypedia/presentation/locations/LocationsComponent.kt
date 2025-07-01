package ru.kanogor.rickandmortypedia.presentation.locations

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.domain.entity.LocationData

interface LocationsComponent {

    val locations: Flow<PagingData<LocationData>>

}

class LocationsComponentImpl(
    private val locationsData: Flow<PagingData<LocationData>>,
    componentContext: ComponentContext
) : ComponentContext by componentContext, LocationsComponent {

    override val locations: Flow<PagingData<LocationData>> = locationsData
}