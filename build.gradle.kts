plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.devtoolsKsp) apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
}