package ru.kanogor.rickandmortypedia.entity

interface RickAndMortyCharacters {
    val info: Info
    val results: List<Results>
}

interface Info {
    val count: Int
    val pages: Int
    val next: String?
    val prev: String?
}

interface Results {
    val id: Int
    val name: String
    val status: String
    val species: String
    val type: String
    val gender: String
    val origin: Origin
    val location: Location
    val image: String
    val episode: List<String>
    val url: String
    val created: String
}

interface Location {
    val name: String
    val url: String
}

interface Origin {
    val name: String
    val url: String
}

