package arcanegolem.yms.settings.di

interface SettingsDependenciesProvider {
  fun resolveSettingsDependencies() : SettingsDependencies
}