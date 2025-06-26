package ru.kanogor.rickandmortypedia.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kanogor.rickandmortypedia.presentation.MainViewModel

val appModule = module {

    viewModel {
        MainViewModel(
            getRickAndMortyCharactersUseCase = get(),
            getRickAndMortyLocationsUseCase = get()
        )
    }

}