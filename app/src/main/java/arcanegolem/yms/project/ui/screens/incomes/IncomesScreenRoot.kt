package arcanegolem.yms.project.ui.screens.incomes

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
import arcanegolem.yms.project.ui.components.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.project.ui.components.top_bar.ProvideYMSTopAppBarTitle

@Composable
fun IncomesScreenRoot(
  navController: NavController,
  viewModel: IncomesViewModel
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
