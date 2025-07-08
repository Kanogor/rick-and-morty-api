plugins {
    alias(libs.plugins.kanogor.lib)
    alias(libs.plugins.kanogor.decompose)
}

android.namespace = moduleNamespace.versions.common.utils.get()

dependencies {

    // Libs
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
}