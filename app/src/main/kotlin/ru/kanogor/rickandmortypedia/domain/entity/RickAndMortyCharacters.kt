package ru.kanogor.rickandmortypedia.domain.entity

interface RickAndMortyCharacters {
    val infoChar: InfoChar
    val results: List<CharacterData>
}

interface InfoChar {
    val count: Int
    val pages: Int
    val next: String?
    val prev: String?
}

interface CharacterData {
    val id: Int
    val name: String
    val status: String
    val species: String
    val type: String
    val gender: String
    val origin: Origin
    val locationCharData: LocationCharData
    val image: String
    val episode: List<String>
    val url: String
    val created: String
}

interface LocationCharData {
    val name: String
    val url: String
}

interface Origin {
    val name: String
    val url: String
}

