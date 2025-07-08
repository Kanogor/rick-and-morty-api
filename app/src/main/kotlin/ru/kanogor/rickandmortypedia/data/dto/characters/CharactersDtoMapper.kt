package ru.kanogor.rickandmortypedia.data.dto.characters

import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.CharactersPaging
import ru.kanogor.rickandmortypedia.domain.entity.Gender
import ru.kanogor.rickandmortypedia.domain.entity.InfoChar
import ru.kanogor.rickandmortypedia.domain.entity.LocationCharData
import ru.kanogor.rickandmortypedia.domain.entity.Origin
import ru.kanogor.rickandmortypedia.domain.entity.Status

fun OriginDto.mapToDomain() = Origin(
    name = this.name,
    url = this.url
)

fun LocationCharDataDto.mapToDomain() = LocationCharData(
    name = this.name,
    url = this.url
)

fun CharactersPagingDto.mapToDomain() = CharactersPaging(
    infoChar = this.infoChar.mapToDto(),
    results = this.results.mapToListDomain()
)

fun InfoCharDto.mapToDto() = InfoChar(
    count = this.count,
    next = this.next,
    pages = this.pages,
    prev = this.prev
)

fun List<CharacterDataDto>.mapToListDomain() = this.map { it.mapToDomain() }

fun CharacterDataDto.mapToDomain() = CharacterData(
    id = this.id,
    name = this.name,
    status = this.status.mapToEnumStatus(),
    species = this.species,
    type = this.type,
    gender = this.gender.mapToEnumGender(),
    origin = this.origin.mapToDomain(),
    locationCharData = this.locationCharData.mapToDomain(),
    image = this.image,
    episode = this.episode,
    url = this.url,
    created = this.created,
)

fun String.mapToEnumStatus(): Status {
    return this.let { text ->
        Status.valueOf(
            text.uppercase()
        )
    }
}

fun String.mapToEnumGender(): Gender {
    return this.let { text ->
        Gender.valueOf(
            text.uppercase()
        )
    }
}