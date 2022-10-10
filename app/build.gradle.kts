plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

kotlin.sourceSets {
    debug {
        kotlin.srcDir("build/generated/ksp/debug/kotlin")
    }
    release {
        kotlin.srcDir("build/generated/ksp/release/kotlin")
    }
}

android {
    compileSdk = 33

    buildFeatures {
        viewBinding = true
        compose = true
    }

    defaultConfig {
        applicationId = "nl.rhaydus.pokedex"
        minSdk = 21
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    namespace = "nl.rhaydus.pokedex"
}

dependencies {
    // Used for the color resources
    implementation("com.google.android.material:material:1.6.1")

    // Used for the API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Used for the conversion
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Used to load images
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Compose Nav Destinations
    implementation("io.github.raamcosta.compose-destinations:core:1.7.21-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.7.21-beta")

    // Compose dependencies
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("androidx.compose.material:material:1.2.1")
    implementation("androidx.compose.animation:animation:1.2.1")
    implementation("androidx.compose.ui:ui-tooling:1.2.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    // Required to use LiveData as state
    implementation("androidx.compose.runtime:runtime-livedata:1.3.0-rc01")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.42")
    kapt("com.google.dagger:hilt-android-compiler:2.42")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Room
    implementation("androidx.room:room-runtime:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")

    // Used for logging
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Status bar customization
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.23.1")

    // Used for testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    // Used for the assertions
    testImplementation("io.kotest:kotest-assertions-core:5.4.2")

    // Used for mocking
    testImplementation("io.mockk:mockk:1.13.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}