package arcanegolem.yms.account.di

import arcanegolem.yms.core.di.ViewModelFactory
import dagger.Component

@AccountScope
@Component(
  modules = [AccountViewModelsBindingModule::class],
  dependencies = [AccountDependencies::class]
)
interface AccountComponent {
  fun viewModelFactory() : ViewModelFactory
}