package ru.kanogor.rickandmortypedia.convention.config

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            apiVersion.set(Config.kotlinVersion)
            languageVersion.set(Config.kotlinVersion)
            jvmTarget.set(Config.jvmTarget)
        }
    }
}