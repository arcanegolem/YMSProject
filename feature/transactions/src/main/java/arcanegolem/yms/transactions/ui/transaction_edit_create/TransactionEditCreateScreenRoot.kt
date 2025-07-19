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
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarNavAction
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarTitle
import arcanegolem.yms.transactions.di.resolveDaggerComponent
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

  val onScreenExit : () -> Unit = {
    navController.popBackStack()
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
      onClick = { navController.popBackStack() }
    ) { Icon(painter = painterResource(R.drawable.cancel), contentDescription = null) }
  }

  TransactionEditCreateScreen(route, state, viewModel::processEvent, onScreenExit, isEdit)
}