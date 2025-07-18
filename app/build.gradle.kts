plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //For plugin KSP for Room
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.undef.localhandsbrambillafunes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.undef.localhandsbrambillafunes"
        minSdk = 26
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    val roomVersion = "2.7.2"

    //For Room library for database
    implementation("androidx.room:room-runtime:$roomVersion")
    //Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    //Plugin for Room
    ksp("androidx.room:room-compiler:$roomVersion")
    //Paging 3 Integration
    implementation("androidx.room:room-paging:$roomVersion")
    implementation("io.coil-kt.coil3:coil-compose:3.2.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Agregar Dependencia de navegacion de Jetpack Compose
    implementation(libs.androidx.navigation.compose)
    //Dependencia para utilizar Visibilities en login screen
    implementation(libs.compose.icons.extended)
    //Dependencia para utilizar rememberAsyncImagePainter
    implementation(libs.coil.compose)

    implementation(libs.androidx.ui.tooling.preview) // Para @Preview
    debugImplementation(libs.androidx.ui.tooling)    // Para ver Previews en Android Studio

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}