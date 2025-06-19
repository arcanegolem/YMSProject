package arcanegolem.yms.project.incomes

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import arcanegolem.yms.project.R
import arcanegolem.yms.project.navigation.routes.History
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun IncomesScreenRoot(
  navController: NavController,
  viewModel: IncomesViewModel = koinViewModel()
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.incomes_title)) }
  ProvideYMSTopAppBarActions {
    IconButton(
      onClick = { navController.navigate(History(true)) }
    ) { Icon(painter = painterResource(R.drawable.history), contentDescription = null) }
  }

  val state = viewModel.state.collectAsState()

  IncomesScreen(state, viewModel::processEvent)
}