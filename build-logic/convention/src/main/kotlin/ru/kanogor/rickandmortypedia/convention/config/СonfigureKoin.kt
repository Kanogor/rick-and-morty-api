package ru.kanogor.rickandmortypedia.convention.config

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.kanogor.rickandmortypedia.convention.utils.LibAlias
import ru.kanogor.rickandmortypedia.convention.utils.PluginAlias
import ru.kanogor.rickandmortypedia.convention.utils.implementation

internal fun Project.configureKoin() {
    pluginManager.apply {
        apply(PluginAlias.GOOGLE_KSP)
    }

    dependencies {
        implementation(LibAlias.KOIN_ANDROID)
        implementation(LibAlias.KOIN_ANNOTATIONS)
        implementation(LibAlias.KOIN_COMPOSE)
        implementation(LibAlias.KOIN_COMPOSE_NAV)
        implementation(LibAlias.KOIN_COMPOSE)
    }
}