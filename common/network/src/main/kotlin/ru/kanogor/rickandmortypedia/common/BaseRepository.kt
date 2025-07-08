package ru.kanogor.rickandmortypedia.common

import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

open class BaseRepository {
    suspend fun <K : Any> saveApiSuspendResult(
        call: suspend () -> Response<K>?
    ): ResponseStatus<K> {
        val response: Response<K>?
        val error: DtoError.ErrorResponse?

        return try {
            response = call.invoke()
            if (response != null && response.isSuccessful) {
                return ResponseStatus.Success(data = response.body())
            }
            val errorBody = response?.errorBody()?.string()
            error = errorBody?.let {
                Json.decodeFromString<DtoError.ErrorResponse>(it)
            }
            ResponseStatus.Error(
                ExceptionMessage(
                    message = error?.error,
                    cause = Throwable(errorBody)
                )
            )
        } catch (e: Exception) {
            when (e) {
                is ApiErrorException ->
                    ResponseStatus.Error(
                        ExceptionMessage(
                            message = e.message,
                            cause = Throwable(e)
                        )
                    )

                is UnknownHostException -> ResponseStatus.Error(
                    NetworkException(e.message, Throwable(e))
                )

                is HttpException -> {
                    ResponseStatus.Error(
                        NetworkException(
                            e.response()?.errorBody()?.toString() ?: "",
                            Throwable(e)
                        )
                    )
                }

                else -> {
                    ResponseStatus.Error(
                        NoNetworkException(null, Throwable(e))
                    )
                }
            }
        }
    }

    protected fun <T : Any, R : Any> ResponseStatus<T>.asEntity(
        data: R? = null,
        error: String = BASE_ERROR_MESSAGE
    ): Entity<R> = when (this) {
        is ResponseStatus.Success -> data?.let { Entity.Success(it) } ?: Entity.Error(error)
        is ResponseStatus.Error -> Entity.Error(this.exception.message ?: error)
    }

    companion object {
        const val BASE_ERROR_MESSAGE = "Что-то пошло не так"
    }

}