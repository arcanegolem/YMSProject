package arcanegolem.yms.project.ui.screens.account_edit

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import arcanegolem.yms.project.R
import arcanegolem.yms.project.navigation.routes.AccountEdit
import arcanegolem.yms.project.ui.components.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.project.ui.components.top_bar.ProvideYMSTopAppBarNavAction
import arcanegolem.yms.project.ui.components.top_bar.ProvideYMSTopAppBarTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountEditScreenRoot(
  navController: NavController,
  route : AccountEdit,
  viewModel: AccountEditViewModel = koinViewModel()
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.account_title)) }

  val state = viewModel.state.collectAsStateWithLifecycle()

  ProvideYMSTopAppBarActions {
    IconButton(
      onClick = {
        viewModel.processEvent(
          AccountEditEvent.ConsumeUpdates { navController.navigateUp() }
        )
      }
    ) { Icon(painter = painterResource(R.drawable.check), contentDescription = null) }
  }
  ProvideYMSTopAppBarNavAction {
    IconButton(
      onClick = { navController.navigateUp() }
    ) { Icon(painter = painterResource(R.drawable.cancel), contentDescription = null) }
  }

  AccountEditScreen(route, state, viewModel::processEvent)
}
