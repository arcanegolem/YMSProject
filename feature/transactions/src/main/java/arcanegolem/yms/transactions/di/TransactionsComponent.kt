package arcanegolem.yms.transactions.di

import arcanegolem.yms.core.di.ViewModelFactory
import dagger.Component

@TransactionsScope
@Component(
  modules = [TransactionsViewModelsBindingModule::class],
  dependencies = [TransactionsDependencies::class]
)
interface TransactionsComponent {
  fun viewModelFactory() : ViewModelFactory
}