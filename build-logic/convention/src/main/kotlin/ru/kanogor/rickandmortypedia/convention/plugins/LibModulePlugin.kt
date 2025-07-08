package ru.kanogor.rickandmortypedia.convention.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.kanogor.rickandmortypedia.convention.config.configureCommon
import ru.kanogor.rickandmortypedia.convention.utils.PluginAlias

class LibModulePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            pluginManager.apply{
                apply(PluginAlias.ANDROID_LIBRARY)
                apply(PluginAlias.KOTLIN_ANDROID)
            }
            extensions.configure<LibraryExtension>{
                configureCommon(this)
            }
        }
    }
}