package arcanegolem.yms.transactions.di

import androidx.lifecycle.ViewModel
import arcanegolem.yms.core.di.ViewModelKey
import arcanegolem.yms.transactions.ui.analysis.AnalysisViewModel
import arcanegolem.yms.transactions.ui.expenses.ExpensesViewModel
import arcanegolem.yms.transactions.ui.history.HistoryViewModel
import arcanegolem.yms.transactions.ui.incomes.IncomesViewModel
import arcanegolem.yms.transactions.ui.transaction_edit_create.TransactionEditCreateViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TransactionsViewModelsBindingModule {
  @[Binds IntoMap ViewModelKey(IncomesViewModel::class)]
  fun bindIncomesViewModelToViewModel(viewModel: IncomesViewModel) : ViewModel

  @[Binds IntoMap ViewModelKey(ExpensesViewModel::class)]
  fun bindExpensesViewModelToViewModel(viewModel: ExpensesViewModel) : ViewModel

  @[Binds IntoMap ViewModelKey(HistoryViewModel::class)]
  fun bindHistoryViewModelToViewModel(viewModel: HistoryViewModel) : ViewModel

  @[Binds IntoMap ViewModelKey(TransactionEditCreateViewModel::class)]
  fun bindTransactionEditCreateViewModelToViewModel(viewModel: TransactionEditCreateViewModel) : ViewModel

  @[Binds IntoMap ViewModelKey(AnalysisViewModel::class)]
  fun bindAnalysisViewModelToViewModel(viewModel: AnalysisViewModel) : ViewModel
}