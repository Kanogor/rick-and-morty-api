package ru.kanogor.rickandmortypedia.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.kanogor.rickandmortypedia.presentation.characters.CharactersViewModel
import ru.kanogor.rickandmortypedia.presentation.locations.LocationsViewModel
import ru.kanogor.rickandmortypedia.presentation.singleCharacter.SingleCharacterViewModel

val appModule = module {

    viewModel {
        CharactersViewModel(
            getRickAndMortyCharactersUseCase = get(),
        )
    }

    viewModel {
        LocationsViewModel(
            getRickAndMortyLocationsUseCase = get(),
        )
    }

    viewModel {
        SingleCharacterViewModel(
            getSingleCharacterUseCase = get(),
            getEpisodesUseCase = get()
        )
    }
}