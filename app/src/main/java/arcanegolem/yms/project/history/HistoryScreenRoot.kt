package arcanegolem.yms.project.history

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import arcanegolem.yms.project.R
import arcanegolem.yms.project.navigation.routes.History
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarNavAction
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryScreenRoot(
  navController: NavController,
  route : History,
  viewModel: HistoryViewModel = koinViewModel()
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.history_title)) }
  ProvideYMSTopAppBarActions {
    IconButton(onClick = {}) { Icon(painter = painterResource(R.drawable.analysis), contentDescription = null) }
  }
  ProvideYMSTopAppBarNavAction {
    IconButton(
      onClick = { navController.navigateUp() }
    ) { Icon(painter = painterResource(R.drawable.arrow_back), contentDescription = null) }
  }

  val state = viewModel.state.collectAsStateWithLifecycle()

  HistoryScreen(route, state, viewModel::processEvent)
}