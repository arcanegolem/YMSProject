pluginManagement {
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "YMSProject"
include(":app")
include(":feature:transactions")
include(":feature:categories")
include(":feature:account")
include(":feature:settings")
include(":core:data")
include(":core:ui")
include(":core:utils")
include(":core:di")
include(":graph")
include(":core:domain")
