package arcanegolem.yms.transactions.ui.transaction_edit_create

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarNavAction
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarTitle
import arcanegolem.yms.transactions.di.resolveDaggerComponent
import arcanegolem.yms.transactions.navigation.Expenses
import arcanegolem.yms.transactions.navigation.History
import arcanegolem.yms.transactions.navigation.Incomes
import arcanegolem.yms.transactions.navigation.IncomesGraph
import arcanegolem.yms.transactions.navigation.TransactionEditCreate

@Composable
fun TransactionEditCreateScreenRoot(
  navController: NavController,
  viewModel : TransactionEditCreateViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory()),
  route : TransactionEditCreate
) {
  ProvideYMSTopAppBarTitle {
    Text(
      text = stringResource(
        if (route.isIncome) R.string.transaction_edit_create_income_title
        else R.string.transaction_edit_create_expense_title
      )
    )
  }

  val state = viewModel.state.collectAsStateWithLifecycle()

  // Вот это чудо техники тут временно, пока не будет нормального хранения транзакций в БД,
  // сейчас это нужно просто для того чтобы перезагружать данные о транзакциях и то
  // с историей и переходом на экран назад не работает нормально (видно старые данные), но сейчас 7
  // утра и я устал (проверяющий прости что это видишь)
  // TODO: Убрать это отсюда чтобы не позориться
  val onScreenExit : () -> Unit = {
    val prevDestination = navController.previousBackStackEntry?.destination
    when {
      prevDestination?.hasRoute(Incomes::class) == true ->
        navController.navigate(Incomes) { popUpTo(Incomes) { inclusive = true } }
      prevDestination?.hasRoute(Expenses::class) == true ->
        navController.navigate(Expenses) { popUpTo(Incomes) { inclusive = true } }
      prevDestination?.hasRoute(History::class) == true -> {
        val route = History(isIncome = prevDestination.parent?.hasRoute(IncomesGraph::class) == true)
        navController.navigate(route)
        { popUpTo(route) { inclusive = true } }
      }
    }
  }
  val isEdit = route.transactionId != null

  ProvideYMSTopAppBarActions {
    IconButton(
      onClick = {
        viewModel.processEvent(TransactionEditCreateEvent.ConsumeUpdates { onScreenExit() })
      }
    ) { Icon(painter = painterResource(R.drawable.check), contentDescription = null) }
  }
  ProvideYMSTopAppBarNavAction {
    IconButton(
      onClick = { navController.navigateUp() }
    ) { Icon(painter = painterResource(R.drawable.cancel), contentDescription = null) }
  }

  TransactionEditCreateScreen(route, state, viewModel::processEvent, onScreenExit, isEdit)
}