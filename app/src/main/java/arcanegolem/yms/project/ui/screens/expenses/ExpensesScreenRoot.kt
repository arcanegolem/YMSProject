package arcanegolem.yms.project.ui.screens.expenses

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
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExpensesScreenRoot(
  navController: NavController,
  viewModel: ExpensesViewModel = koinViewModel()
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.expenses_title)) }
  ProvideYMSTopAppBarActions {
    IconButton(
      onClick = { navController.navigate(History(false)) }
    ) { Icon(painter = painterResource(R.drawable.history), contentDescription = null) }
  }

  val state = viewModel.state.collectAsStateWithLifecycle()

  ExpensesScreen(state, viewModel::processEvent)
}