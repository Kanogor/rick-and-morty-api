package ru.kanogor.rickandmortypedia.convention.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.InvalidPluginException
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import ru.kanogor.rickandmortypedia.convention.config.configureKoin

class KoinPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            extensions.configure<LibraryExtension>{
                when(val ext = extensions.findByType(CommonExtension::class)){
                    is com.android.build.api.dsl.LibraryExtension -> {
                        configureKoin()
                    }
                    is ApplicationExtension -> {
                        configureKoin()
                    }
                    else -> throw InvalidPluginException("extension of type ${(ext?:String)::class.java.name}")
                }
            }
        }
    }
}