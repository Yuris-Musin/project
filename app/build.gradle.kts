
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDevtoolsKsp)
    id("kotlin-parcelize")
}

android {
    namespace = "ru.musindev.myapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.musindev.myapp"
        minSdk = 26
        targetSdk = 34
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
    buildFeatures{
        viewBinding = true
        buildConfig = true
    }
    flavorDimensions += "version"
    productFlavors {
        create("basic") {
            dimension = "version"
            applicationIdSuffix = ".basic"
            versionNameSuffix = "-basic"
        }
        create("pro") {
            dimension = "version"
            applicationIdSuffix = ".pro"
            versionNameSuffix = "-pro"
        }
    }
    sourceSets {
        getByName("basic") {
            java {
                srcDirs("src\\basic\\java")
            }
        }
        getByName("pro") {
            java {
                srcDirs("src\\pro\\java")
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.core)
    implementation (libs.glide)

    implementation (libs.retrofit)
    implementation (libs.gson)
    implementation (libs.squareup.okhttp3)
    implementation(libs.firebase.messaging)

    ksp (libs.ksp.dagger)
    ksp (libs.ksp.room)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.gridlayout)
    implementation(libs.google.dagger)
    implementation(libs.androidx.room)
    implementation(project(":remote_module"))
    implementation ("androidx.room:room-ktx:2.6.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.coroutines)
    implementation(libs.coroutines.core)

    /* RxJava */
    implementation (libs.rxandroid)
    implementation (libs.rxjava)
    implementation (libs.room.rxjava)

    implementation(libs.rxjava.retrofit.adapter)
    implementation (libs.rxkotlin)

}