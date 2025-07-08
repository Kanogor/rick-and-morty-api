package ru.kanogor.rickandmortypedia.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import ru.kanogor.rickandmortypedia.convention.utils.LibAlias
import ru.kanogor.rickandmortypedia.convention.utils.PluginAlias
import ru.kanogor.rickandmortypedia.convention.utils.implementation

internal fun Project.configureCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    pluginManager.apply {
        apply(PluginAlias.JETBRAINS_COMPOSE_COMPILER)
    }

    with(commonExtension) {
        defaultConfig {
            vectorDrawables {
                useSupportLibrary = true
            }
        }
        buildFeatures.compose = true
    }

    dependencies {
        implementation(LibAlias.COMPOSE_UI)
        implementation(LibAlias.COMPOSE_MATERIAL3)
    }
}