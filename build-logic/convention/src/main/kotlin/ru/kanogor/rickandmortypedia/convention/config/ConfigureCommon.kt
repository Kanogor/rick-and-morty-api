package ru.kanogor.rickandmortypedia.convention.config

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.configureCommon(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    this.extensions.create<GradleConfig>("config").apply {
        this.namespacePrefix.convention(Config.NAMESPACE_PREFIX)
    }
    commonExtension.apply {
        compileSdk = Config.COMPILE_SDK
        defaultConfig {
            minSdk = Config.MIN_SDK
        }
        compileOptions {
            sourceCompatibility = Config.javaVersion
            targetCompatibility = Config.javaVersion
        }
    }
    configureKotlin()
}

abstract class GradleConfig {
    internal abstract val namespacePrefix: Property<String>
    infix fun namespace(value: String): String = namespacePrefix.get() + value
}