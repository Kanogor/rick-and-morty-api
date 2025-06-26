package ru.kanogor.rickandmortypedia.domain

import kotlinx.coroutines.delay
import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository
import ru.kanogor.rickandmortypedia.entity.LocationData

class GetRickAndMortyLocationsUseCase(private val repository: RickAndMortyRepository) {

    suspend fun execute(page: Int): List<LocationData> {
        delay(2000)  // для более плавной загрузки страниц
        return repository.getLocations(page).result
    }
}