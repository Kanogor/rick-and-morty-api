package ru.kanogor.rickandmortypedia.di

import org.koin.dsl.module
import ru.kanogor.rickandmortypedia.domain.GetRickAndMortyCharactersUseCase


val domainModule = module {

    factory<GetRickAndMortyCharactersUseCase> {
        GetRickAndMortyCharactersUseCase(repository = get())
    }

}