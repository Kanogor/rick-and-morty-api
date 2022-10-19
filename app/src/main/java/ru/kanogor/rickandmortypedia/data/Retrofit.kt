package ru.kanogor.rickandmortypedia.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

private const val BASE_URL = "https://rickandmortyapi.com/api/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideSearchRickAndMorty(retrofit: Retrofit) : SearchRickAndMorty =
        retrofit.create(SearchRickAndMorty::class.java)

    @Singleton
    @Provides
    fun provideRepository(searchRickAndMorty: SearchRickAndMorty) = RickAndMortyRepository(searchRickAndMorty)
}

interface SearchRickAndMorty {

    @GET("character")
   suspend fun getCharacters() : Response<RickAndMortyCharactersDto>

}