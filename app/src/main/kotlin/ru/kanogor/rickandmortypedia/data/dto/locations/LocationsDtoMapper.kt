package ru.kanogor.rickandmortypedia.data.dto.locations

import ru.kanogor.rickandmortypedia.domain.entity.InfoLocation
import ru.kanogor.rickandmortypedia.domain.entity.LocationData
import ru.kanogor.rickandmortypedia.domain.entity.Locations

fun LocationsDto.mapToDomain() = Locations(
    info = this.info.mapToDomain(),
    result = this.result.mapToDomainList()
)

fun List<LocationDataDto>.mapToDomainList() = this.map { it.mapToDomain() }

fun LocationDataDto.mapToDomain() = LocationData(
    id = this.id,
    name = this.name,
    dimension = this.dimension,
    type = this.type
)

fun InfoLocationDto.mapToDomain() = InfoLocation(
    count = this.count,
    pages = this.pages
)