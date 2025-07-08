package ru.kanogor.rickandmortypedia.common

import java.io.IOException

class ExceptionMessage(
    override val message: String?,
    override val cause: Throwable,
) : Exception(message, cause)

class NetworkException(
    override val message: String?,
    override val cause: Throwable,
) : Exception(message, cause)

class NoNetworkException(
    override val message: String?,
    override val cause: Throwable
) : Exception(message, cause)

class ApiErrorException(override val message: String) : IOException(message)