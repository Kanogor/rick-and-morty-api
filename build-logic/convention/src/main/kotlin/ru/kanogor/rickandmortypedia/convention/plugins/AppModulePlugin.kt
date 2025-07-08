package ru.kanogor.rickandmortypedia.convention.plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.kanogor.rickandmortypedia.convention.config.Config
import ru.kanogor.rickandmortypedia.convention.config.configureCommon
import ru.kanogor.rickandmortypedia.convention.config.configureCompose
import ru.kanogor.rickandmortypedia.convention.utils.PluginAlias
import java.io.FileInputStream
import java.util.Properties

class AppModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            this.applyPlugins()
            extensions.configure<BaseAppModuleExtension> {
                configureCommon(this)
                configureCompose(this)
                applyFlavors()
                applySigningConfigs(this@with)
                applyBuildTypes()
                applyOutputFileName()

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }

    private fun BaseAppModuleExtension.applyOutputFileName() = applicationVariants.all {
        outputs.all {
            if (this is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                val appName = Config.APP_NAME
                val appVersion = versionName
                val buildType = buildType.name
                outputFileName = "$appName-$appVersion-$buildType.apk"
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
                versionName = Config.versionName()
                manifestPlaceholders.apply {
                    put(appLabelKey, Config.APP_NAME)
                }
                buildConfigField(
                    FlavorsField.BACKEND_URL.first,
                    FlavorsField.BACKEND_URL.second,
                    Config.BASE_URL
                )
            }
        }
    }

    private fun BaseAppModuleExtension.applySigningConfigs(project: Project) = this.signingConfigs {
        val keystoreFileName = "${project.projectDir.absolutePath}/config/keystore.properties"
        val keystoreProperties = Properties().apply {
            load(FileInputStream(project.file(keystoreFileName)))
        }

        create("release") {
            with(keystoreProperties) {
                storeFile = project.file(getProperty("storeFile"))
                storePassword = getProperty("storePassword")
                keyAlias = getProperty("keyAlias")
                keyPassword = getProperty("keyPassword")
            }
        }
    }

    private fun BaseAppModuleExtension.applyBuildTypes() = this.buildTypes {

        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    companion object {
        private object FlavorsField {
            // first is Type second is Name
            val BACKEND_URL = "String" to "BASE_URL"
        }
    }

}