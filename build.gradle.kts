// Root build.gradle.kts

plugins {
    // No plugins here, apply in modules
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
    }
}

allprojects {

}
