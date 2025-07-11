package arcanegolem.yms.transactions.ui.incomes

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
fun IncomesScreenRoot(
  navController: NavController,
  viewModel : IncomesViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory())
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.incomes_title)) }
  ProvideYMSTopAppBarActions {
    IconButton(
      onClick = { navController.navigate(History(true)) }
    ) { Icon(painter = painterResource(R.drawable.history), contentDescription = null) }
  }

  val state = viewModel.state.collectAsStateWithLifecycle()

  IncomesScreen(state, viewModel::processEvent)
}
