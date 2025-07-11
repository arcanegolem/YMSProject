package arcanegolem.yms.transactions.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun resolveDaggerComponent() : TransactionsComponent {
  val context = LocalContext.current.applicationContext
  val dependencies = remember {
    (context as TransactionsDependenciesProvider).resolveTransactionsDependencies()
  }
  return remember(dependencies) {
    DaggerTransactionsComponent.builder().transactionsDependencies(dependencies).build()
  }
}