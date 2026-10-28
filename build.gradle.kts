// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.ksp) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.10.2" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "2.3.20" apply false
    alias(libs.plugins.google.gms.google.services) apply false
}