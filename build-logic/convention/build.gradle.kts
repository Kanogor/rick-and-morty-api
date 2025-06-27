plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.jetbrains.kotlinGradle)
}

gradlePlugin {
    plugins {
        register("appModulePlugin") {
            id = "kanogor.app"
            implementationClass = "ru.kanogor.rickandmortypedia.convention.plugins.AppModulePlugin"
        }
        register("libModulePlugin") {
            id = "kanogor.libs"
            implementationClass = "ru.kanogor.rickandmortypedia.convention.plugins.LibModulePlugin"
        }
        register("composeCorePlugin") {
            id = "kanogor.compose"
            implementationClass = "ru.kanogor.rickandmortypedia.convention.plugins.ComposeCorePlugin"
        }
        register("navigationPlugin") {
            id = "kanogor.navigation"
            implementationClass = "ru.kanogor.rickandmortypedia.convention.plugins.NavigationPlugin"
        }
        register("koinPlugin") {
            id = "kanogor.koin"
            implementationClass = "ru.kanogor.rickandmortypedia.convention.plugins.KoinPlugin"
        }
    }
}
