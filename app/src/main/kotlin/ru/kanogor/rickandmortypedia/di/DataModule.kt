package ru.kanogor.rickandmortypedia.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.kanogor.rickandmortypedia.BuildConfig
import ru.kanogor.rickandmortypedia.data.RickAndMortyRepository
import ru.kanogor.rickandmortypedia.data.SearchRickAndMorty

val dataModule = module {

    single {
        RickAndMortyRepository(searchRickAndMorty = get())
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    factory {
        get<Retrofit>().create(SearchRickAndMorty::class.java)
    }

}