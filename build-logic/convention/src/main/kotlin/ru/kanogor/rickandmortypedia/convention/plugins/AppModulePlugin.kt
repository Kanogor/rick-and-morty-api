package ru.kanogor.rickandmortypedia.convention.plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.kanogor.rickandmortypedia.convention.config.Config
import ru.kanogor.rickandmortypedia.convention.config.configureCommon
import ru.kanogor.rickandmortypedia.convention.config.configureCompose
import ru.kanogor.rickandmortypedia.convention.utils.PluginAlias

class AppModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            this.applyPlugins()
            extensions.configure<BaseAppModuleExtension> {
                configureCommon(this)
                configureCompose(this)
                applyFlavors()
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }

    private fun Project.applyPlugins() = with(pluginManager) {
        apply(PluginAlias.ANDROID_APPLICATION)
        apply(PluginAlias.KOTLIN_ANDROID)
        apply(PluginAlias.JETBRAINS_COMPOSE_COMPILER)
    }

    private fun BaseAppModuleExtension.applyFlavors() {
        flavorDimensions.add(Config.DIMENSIONS)
        this.productFlavors {
            val appLabelKey = "appLabel"

            defaultConfig {
                buildFeatures.buildConfig = true
                targetSdk = Config.TARGET_SDK
                versionCode = Config.CODE_VERSION
                versionName = Config.VERSION_NAME
                manifestPlaceholders.apply {
                    put(appLabelKey, "RickAndMortyPedia")
                }
            }
        }
    }
}