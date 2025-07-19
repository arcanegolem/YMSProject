package arcanegolem.yms.transactions.ui.history

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
import arcanegolem.yms.transactions.navigation.Analysis
import arcanegolem.yms.transactions.navigation.History

@Composable
fun HistoryScreenRoot(
  navController: NavController,
  route : History,
  viewModel : HistoryViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory())
) {

  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.history_title)) }
  ProvideYMSTopAppBarActions {
    IconButton(onClick = { navController.navigate(Analysis(route.isIncome)) }) {
      Icon(painter = painterResource(R.drawable.analysis), contentDescription = null)
    }
  }
  ProvideYMSTopAppBarNavAction {
    IconButton(
      onClick = { navController.popBackStack() }
    ) { Icon(painter = painterResource(R.drawable.arrow_back), contentDescription = null) }
  }

  val state = viewModel.state.collectAsStateWithLifecycle()

  HistoryScreen(navController, route, state, viewModel::processEvent)
}
