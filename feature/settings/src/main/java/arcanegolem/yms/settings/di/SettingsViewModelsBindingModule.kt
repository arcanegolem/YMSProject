package arcanegolem.yms.settings.di

import androidx.lifecycle.ViewModel
import arcanegolem.yms.core.di.ViewModelKey
import arcanegolem.yms.settings.ui.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface SettingsViewModelsBindingModule {
  @[Binds IntoMap ViewModelKey(SettingsViewModel::class)]
  fun bindSettingsViewModelToViewModel(viewModel: SettingsViewModel) : ViewModel
}