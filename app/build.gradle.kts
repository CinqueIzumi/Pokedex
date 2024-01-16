plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
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
    compileSdk = 34

    buildFeatures {
        compose = true
    }

    defaultConfig {
        applicationId = "nl.rhaydus.pokedex"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    namespace = "nl.rhaydus.pokedex"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Life cycle management compose
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Splash Screen API
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2023.10.00")

    // Compose
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material:material-icons-extended")

    // Compose UI debugging
    debugImplementation(composeBom)
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Datastore
    implementation("androidx.datastore:datastore:1.0.0")

    // Kotlinx
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    // Json serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.google.code.gson:gson:2.9.0")

    // Accompanist - Required for custom status bar color
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    // Navigation
    val composeDestinationsVersion = "1.9.53"
    implementation("io.github.raamcosta.compose-destinations:animations-core:$composeDestinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$composeDestinationsVersion")

    // Room
    val roomVersion = "2.6.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // Hilt
    val hiltVersion = "2.48"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:26.1.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-perf")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Timber logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Unit tests dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("io.kotest:kotest-assertions-core:5.4.2")
    testImplementation("io.mockk:mockk:1.13.7")
}

tasks.withType<Test> {
    useJUnitPlatform()
}