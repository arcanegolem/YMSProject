package arcanegolem.yms.settings.di

import arcanegolem.yms.settings.domain.repos.SettingsRepository

interface SettingsDependencies {
  fun resolveSettingsRepository() : SettingsRepository
}