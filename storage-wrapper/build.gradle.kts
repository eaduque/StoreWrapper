plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.serialization)
}

group = "com.meli.storage"
version = "1.0.0"

android {
  namespace = "com.meli.storage.wrapper"
  compileSdk = 35

  defaultConfig {
    // Se agrega minSdk como 19 ya que es el minSdk que soporta DataStore
    minSdk = 19
    consumerProguardFiles("consumer-rules.pro")
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

  lint {
    disable.addAll(listOf("TypographyFractions", "TypographyQuotes"))
    enable.addAll(listOf("RtlHardcoded", "RtlCompat", "RtlEnabled"))
    checkOnly.addAll(listOf("NewApi", "InlinedApi"))
    quiet = true
    abortOnError = true
    ignoreWarnings = false
    checkDependencies = true
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.datastore.preferences.core)
  api(libs.kotlinx.serialization.json)

  testImplementation(libs.junit)
  testImplementation(libs.kotlin.test.junit)
  testImplementation(libs.mockk)
  testImplementation(libs.kotlinx.coroutines.test)
}
