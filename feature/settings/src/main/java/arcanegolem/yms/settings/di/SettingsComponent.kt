package arcanegolem.yms.settings.di

import arcanegolem.yms.core.di.ViewModelFactory
import dagger.Component

@SettingsScope
@Component(
  modules = [
    SettingsViewModelsBindingModule::class
  ],
  dependencies = [SettingsDependencies::class]
)
interface SettingsComponent {
  fun viewModelFactory() : ViewModelFactory
}