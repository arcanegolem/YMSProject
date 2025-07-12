package arcanegolem.yms.transactions.ui.expenses

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
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarTitle
import arcanegolem.yms.transactions.di.resolveDaggerComponent
import arcanegolem.yms.transactions.navigation.History

@Composable
fun ExpensesScreenRoot(
  navController: NavController,
  viewModel : ExpensesViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory())
) {

  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.expenses_title)) }
  ProvideYMSTopAppBarActions {
    IconButton(
      onClick = { navController.navigate(History(false)) }
    ) { Icon(painter = painterResource(R.drawable.history), contentDescription = null) }
  }

  val state = viewModel.state.collectAsStateWithLifecycle()

  ExpensesScreen(navController, state, viewModel::processEvent)
}
