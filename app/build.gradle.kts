plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.maps)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.tenretni"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.tenretni"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)


    // Core runtime for Jetpack Navigation 3 library — provides navigation components and APIs
    implementation(libs.androidx.navigation3.runtime)

    // UI components for Navigation 3 — includes NavDisplay etc.
    implementation(libs.androidx.navigation3.ui)

    // ViewModel integration with Navigation 3 — provides lifecycle-aware ViewModels scoped to navigation destinations
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.kotlinx.datetime)
    implementation(libs.androidx.material.icons.extended)

    //Bibliothèque Fuel pour les requêtes HTTP
    implementation(libs.fuel.android)
    implementation(libs.fuel.json)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.coil.compose)

    //https://developers.google.com/codelabs/maps-platform/maps-platform-101-compose#0
    implementation(libs.maps.compose)
    // Google Maps Compose utility library
    implementation(libs.maps.compose.utils)
    // Google Maps Compose widgets library
    implementation(libs.maps.compose.widgets)

    //Code QR => https://github.com/G00fY2/quickie
    //https://barcode.tec-it.com/en/QRCode?data=ycharron
    implementation(libs.quickie.bundled)

    //https://github.com/PierfrancescoSoffritti/android-youtube-player
    implementation(libs.androidyoutubeplayer.core)
    implementation(libs.androidyoutubeplayer.chromecast)

    implementation(libs.coil.compose)

    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.compose.ui.text.google.fonts)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}


