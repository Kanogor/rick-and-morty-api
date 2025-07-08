package ru.kanogor.rickandmortypedia.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

interface DtoError {

    @JsonClass(generateAdapter = true)
    data class ErrorResponse(
        @Json(name = "error") val error: String? = "",
    )

}