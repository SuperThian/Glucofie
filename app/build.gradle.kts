plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-kapt")}

android {
    namespace = "com.capstone.glucofie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.capstone.glucofie"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
        mlModelBinding = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    api(libs.edmodo.android.image.cropper)
    implementation("com.vanniktech:android-image-cropper:4.5.0")
    implementation("com.squareup.picasso:picasso:2.5.2")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.cardview)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.tensorflow.lite.gpu)
    implementation(libs.play.services.vision)
    testImplementation("junit:junit:4.13.2")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.activity:activity-ktx:1.7.2")

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("androidx.room:room-runtime:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    testImplementation("androidx.arch.core:core-testing:2.2.0") // InstantTaskExecutorRule
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1") //TestDispatcher
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-inline:3.12.4")

    implementation("androidx.room:room-paging:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    androidTestImplementation ("com.android.support.test.espresso:espresso-contrib:3.0.2") //RecyclerViewActions

    implementation ("androidx.test.espresso:espresso-idling-resource:3.5.1")

    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")//IntentsTestRule

    androidTestImplementation  ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")  //TestDispatcher

    implementation("org.tensorflow:tensorflow-lite-task-vision:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.4.4")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")

    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    kapt ("com.github.bumptech.glide:compiler:4.12.0")
}