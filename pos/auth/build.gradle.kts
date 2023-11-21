import net.nomia.gradle.GradleExtension.getGeneralKotlinConfigure
import net.nomia.gradle.LibraryGradleExtension.applyComposeConfig
import net.nomia.gradle.LibraryGradleExtension.applyDefaultConfig

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id(libs.plugins.androidLibrary.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
    id(libs.plugins.kotlinKapt.get().pluginId)
    id(libs.plugins.hilt.get().pluginId)
    alias(libs.plugins.ksp)
}

android {
    applyDefaultConfig(
        namespace = "net.nomia.auth",
    )
    kotlinOptions(
        configure = getGeneralKotlinConfigure()
    )
    applyComposeConfig(
        versionCompose = libs.versions.compose.get()
    )
}

hilt {
    enableAggregatingTask = true
}

dependencies {

    implementation(libs.core)
    implementation(libs.activity.appcompat)
    implementation(libs.material)
    implementation(libs.hilt.navigation.compose) {
        exclude(group = "androidx.navigation", module = "navigation-compose")
    }

    implementation(platform(libs.compose.bom))
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.bundles.compose)
    implementation(libs.hilt.navigation.compose) {
        exclude(group = "androidx.navigation", module = "navigation-compose")
    }

    implementation(libs.composeAnnotation)
    ksp(libs.composeAnnotationProcessor)
    implementation(libs.ksp)

    implementation(libs.bundles.room)
    kapt(libs.roomCompiler)

    implementation(project(":core:mvi-android"))
    implementation(project(":common-ui"))
    implementation(project(":core:core"))
    implementation(project(":core:ui:compose"))
    implementation(project(":pos:main"))
    implementation(project(":settings"))

    implementation(libs.dagger)
    kapt(libs.daggerCompiler)
    kapt(libs.hiltCompiler)

}

kapt {
    correctErrorTypes = true
}

androidComponents.onVariants {variant ->
    kotlin.sourceSets.findByName(variant.name)?.kotlin?.srcDirs(
        file("$buildDir/generated/ksp/${variant.name}/kotlin")
    )
}

ksp {
    arg("ignoreGenericArgs", "false")
}
