import java.util.Properties

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlinx.serialization)
}

android {
  namespace = "arcanegolem.yms.data"
  compileSdk = 35

  buildFeatures.buildConfig = true

  defaultConfig {
    minSdk = 24

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    buildConfigField("String", "TOKEN", "\"${properties.getProperty("TOKEN")}\"")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  implementation(libs.koin.android)

  implementation(libs.ktor.client.core)
  implementation(libs.ktor.client.okhttp)
  implementation(libs.ktor.serialization.json)
  implementation(libs.ktor.content.negotiation)
  implementation(libs.ktor.client.logging)
  implementation(libs.ktor.client.auth)
  implementation(libs.ktor.client.resources)

  implementation(libs.kotlinx.datetime)

  implementation(project(":domain"))
}