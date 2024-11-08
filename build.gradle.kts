@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application).apply(false)
    //alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.dagger.hilt.android).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.kotlin.android) apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}