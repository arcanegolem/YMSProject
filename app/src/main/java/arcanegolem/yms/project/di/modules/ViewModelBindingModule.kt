package arcanegolem.yms.project.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arcanegolem.yms.project.di.viewmodel.ViewModelFactory
import arcanegolem.yms.project.di.viewmodel.ViewModelKey
import arcanegolem.yms.project.ui.screens.account.AccountViewModel
import arcanegolem.yms.project.ui.screens.account_edit.AccountEditViewModel
import arcanegolem.yms.project.ui.screens.categories.CategoriesViewModel
import arcanegolem.yms.project.ui.screens.expenses.ExpensesViewModel
import arcanegolem.yms.project.ui.screens.history.HistoryViewModel
import arcanegolem.yms.project.ui.screens.incomes.IncomesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelBindingModule {
  @Binds
  fun bindViewModelFactory(factory: ViewModelFactory) : ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(IncomesViewModel::class)
  fun bindIncomesViewModelToViewModel(incomesViewModel: IncomesViewModel) : ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(ExpensesViewModel::class)
  fun bindExpensesViewModelToViewModel(expensesViewModel: ExpensesViewModel) : ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(CategoriesViewModel::class)
  fun bindCategoriesViewModelToViewModel(categoriesViewModel: CategoriesViewModel) : ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(AccountViewModel::class)
  fun bindAccountViewModelToViewModel(accountViewModel: AccountViewModel) : ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(HistoryViewModel::class)
  fun bindHistoryViewModelToViewModel(historyViewModel: HistoryViewModel) : ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(AccountEditViewModel::class)
  fun bindAccountEditViewModelViewModelToViewModel(accountEditViewModel: AccountEditViewModel) : ViewModel
}