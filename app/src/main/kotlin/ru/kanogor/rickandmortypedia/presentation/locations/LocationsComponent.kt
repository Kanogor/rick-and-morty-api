package ru.kanogor.rickandmortypedia.presentation.locations

import com.arkivanov.decompose.ComponentContext

interface LocationsComponent {

    fun onLocationClick(id: Int)

}

class LocationsComponentImpl(
    componentContext: ComponentContext
) : ComponentContext by componentContext, LocationsComponent {

    override fun onLocationClick(id: Int) {
        // TODO пока не реализованно
    }
}