plugins {
    alias(libs.plugins.kanogor.lib)
    alias(libs.plugins.kanogor.decompose)
}

android.namespace = moduleNamespace.versions.common.network.get()

dependencies {

    implementation(projects.common.utils)

    // Libs
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
}