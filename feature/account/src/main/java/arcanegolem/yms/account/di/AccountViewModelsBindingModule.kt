package arcanegolem.yms.account.di

import androidx.lifecycle.ViewModel
import arcanegolem.yms.account.ui.account.AccountViewModel
import arcanegolem.yms.account.ui.account_edit.AccountEditViewModel
import arcanegolem.yms.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AccountViewModelsBindingModule {
  @[Binds IntoMap ViewModelKey(AccountViewModel::class)]
  fun bindAccountViewModelToViewModel(viewModel: AccountViewModel) : ViewModel

  @[Binds IntoMap ViewModelKey(AccountEditViewModel::class)]
  fun bindAccountEditViewModelToViewModel(viewModel: AccountEditViewModel) : ViewModel
}