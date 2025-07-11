import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.detekt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "arcanegolem.yms.data"
  compileSdk = 36

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
  kotlin.compilerOptions {
    jvmTarget = JvmTarget.JVM_11
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  implementation(libs.dagger.android)
  ksp(libs.dagger.android.processor)
  ksp(libs.dagger.compiler)

  api(libs.ktor.client.core)
  api(libs.ktor.client.okhttp)
  api(libs.ktor.serialization.json)
  api(libs.ktor.content.negotiation)
  api(libs.ktor.client.logging)
  api(libs.ktor.client.auth)
  api(libs.ktor.client.resources)

  implementation(libs.kotlinx.datetime)

  api(libs.androidx.datastore.preferences)

  implementation(project(":domain"))
}