package ru.kanogor.rickandmortypedia.convention.config

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.kanogor.rickandmortypedia.convention.utils.LibAlias
import ru.kanogor.rickandmortypedia.convention.utils.implementation

internal fun Project.configureDecompose() {
    dependencies {
        implementation(LibAlias.DECOMPOSE)
        implementation(LibAlias.DECOMPOSE_COMPOSE)
        implementation(LibAlias.ESSENTY)
        implementation(LibAlias.ESSENTY_LIFECYCLE)
    }
}