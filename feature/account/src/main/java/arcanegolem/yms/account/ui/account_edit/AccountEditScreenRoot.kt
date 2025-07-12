package arcanegolem.yms.account.ui.account_edit

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import arcanegolem.yms.account.di.resolveDaggerComponent
import arcanegolem.yms.account.navigation.AccountEdit
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarNavAction
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarTitle

@Composable
fun AccountEditScreenRoot(
  navController: NavController,
  route : AccountEdit,
  viewModel: AccountEditViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory())
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
