package ru.kanogor.rickandmortypedia.convention.config

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.kanogor.rickandmortypedia.convention.utils.LibAlias
import ru.kanogor.rickandmortypedia.convention.utils.implementation

internal fun Project.configureNavigation() {

    dependencies {
        implementation(LibAlias.COMPOSE_NAVIGATION)
    }
}