import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.detekt)
  alias(libs.plugins.ksp)
}

android {
  namespace = "arcanegolem.yms.project"
  compileSdk = 36

  buildFeatures {
    buildConfig = true
  }
  
  androidResources {
    generateLocaleConfig = true
  }

  defaultConfig {
    applicationId = "arcanegolem.yms.project"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

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
  buildFeatures {
    compose = true
  }
}

detekt {
  
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  implementation(libs.androidx.navigation.compose)

  implementation(libs.kotlinx.serialization.json)

  implementation(libs.androidx.core.splashscreen)

  implementation(libs.dagger.android)
  ksp(libs.dagger.android.processor)
  ksp(libs.dagger.compiler)

  implementation(libs.kotlinx.datetime)

  implementation(libs.material.icons.extended)

  implementation(libs.ktor.client.core)
  implementation(libs.ktor.client.okhttp)
  implementation(libs.ktor.serialization.json)
  implementation(libs.ktor.content.negotiation)
  implementation(libs.ktor.client.logging)
  implementation(libs.ktor.client.auth)
  implementation(libs.ktor.client.resources)

  implementation(libs.androidx.datastore.preferences)

  implementation(libs.room.runtime)
  implementation(libs.room.ktx)
  ksp(libs.room.compiler)

  implementation(libs.androidx.work.runtime.ktx)
  
  implementation(libs.androidx.appcompat)

  implementation(project(":feature:account"))
  implementation(project(":feature:categories"))
  implementation(project(":feature:settings"))
  implementation(project(":feature:transactions"))

  implementation(project(":core:ui"))
  implementation(project(":core:di"))
  implementation(project(":core:utils"))
  implementation(project(":core:data"))
}