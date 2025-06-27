package ru.kanogor.rickandmortypedia.convention.config

import org.gradle.api.JavaVersion

internal object Config {
    const val NAMESPACE_PREFIX = "ru.kanogor."
    const val DIMENSIONS = "RickAndMortyPedia"
    const val MIN_SDK = 26
    const val TARGET_SDK = 34
    const val COMPILE_SDK = 35
    const val CODE_VERSION = 4
    const val VERSION_NAME = "2.0"

    val jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    val kotlinVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0
    val javaVersion = JavaVersion.VERSION_17
}
