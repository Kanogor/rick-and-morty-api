package ru.kanogor.rickandmortypedia.di

import org.koin.dsl.module
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyLocationsUseCase


val domainModule = module {

    factory {
        GetRickAndMortyCharactersUseCase(repository = get())
    }

    factory {
        GetRickAndMortyLocationsUseCase(repository = get())
    }

}