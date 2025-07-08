package ru.kanogor.rickandmortypedia.convention.config

import org.gradle.api.JavaVersion

internal object Config {
    const val APP_NAME = "RickAndMortyPedia"
    const val NAMESPACE_PREFIX = "ru.kanogor."
    const val DIMENSIONS = "version"
    const val MIN_SDK = 26
    const val TARGET_SDK = 35
    const val COMPILE_SDK = 36
    const val CODE_VERSION = 20_000_005
    const val BASE_URL = "\"https://rickandmortyapi.com/api/\""

    fun versionName(): String {
        val major = CODE_VERSION / 10_000_000
        val minor = (CODE_VERSION / 10_000) % 1_000
        val patch = CODE_VERSION % 10_000

        return "$major.$minor.$patch"
    }

    val jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    val kotlinVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0
    val javaVersion = JavaVersion.VERSION_17
}
