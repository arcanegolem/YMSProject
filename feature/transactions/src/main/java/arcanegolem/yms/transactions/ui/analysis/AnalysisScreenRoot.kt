package arcanegolem.yms.transactions.ui.analysis

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
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarNavAction
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarTitle
import arcanegolem.yms.transactions.di.resolveDaggerComponent
import arcanegolem.yms.transactions.navigation.Analysis

@Composable
fun AnalysisScreenRoot(
  navController: NavController,
  route : Analysis,
  viewModel : AnalysisViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory())
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.analysis_title)) }
  ProvideYMSTopAppBarNavAction {
    IconButton(
      onClick = { navController.popBackStack() }
    ) { Icon(painter = painterResource(R.drawable.arrow_back), contentDescription = null) }
  }

  val state = viewModel.state.collectAsStateWithLifecycle()

  AnalysisScreen(route, state, viewModel::processEvent)
}