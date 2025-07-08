package ru.kanogor.rickandmortypedia.common

sealed class Entity<out T : Any> {

    data class Success<out T : Any>(
        val data: T,
    ) : Entity<T>()

    data class Error<out T : Any>(
        val code: String = "",
        val message: String = "Error"
    ) : Entity<T>()
}
