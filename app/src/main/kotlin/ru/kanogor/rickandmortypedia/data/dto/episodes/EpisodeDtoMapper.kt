package ru.kanogor.rickandmortypedia.data.dto.episodes

import ru.kanogor.rickandmortypedia.domain.entity.Episode

private fun EpisodeDto.mapToDomain() = Episode(
    name = this.name,
    episodeNum = this.episodeNum,
    airDate = this.airDate
)

fun List<EpisodeDto>.mapToDomainList() = this.map { it.mapToDomain() }