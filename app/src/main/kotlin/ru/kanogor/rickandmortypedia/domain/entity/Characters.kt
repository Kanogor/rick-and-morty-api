package ru.kanogor.rickandmortypedia.domain.entity

data class CharactersPaging(
    val infoChar: InfoChar,
    val results: List<CharacterData>
)

data class InfoChar(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class CharacterData(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: Origin,
    val locationCharData: LocationCharData,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
)

val previewCharacterData = CharacterData(
    created = "12.12.12",
    episode = emptyList(),
    gender = Gender.MALE,
    id = 0,
    image = "",
    locationCharData = LocationCharData("Earth", ""),
    name = "Rick",
    origin = Origin("", ""),
    status = Status.ALIVE,
    species = "Human",
    type = "",
    url = ""
)


enum class Status {
    ALIVE, DEAD, UNKNOWN;

    companion object {
        fun Status.toText(): String {
            return this.name.lowercase().replaceFirstChar { it.uppercase() }
        }
    }
}

enum class Gender {
    FEMALE, MALE, GENDERLESS, UNKNOWN;

    companion object {
        fun Gender.toText(): String {
            return this.name.lowercase().replaceFirstChar { it.uppercase() }
        }
    }
}

data class LocationCharData(
    val name: String,
    val url: String
)

data class Origin(
    val name: String,
    val url: String
)

