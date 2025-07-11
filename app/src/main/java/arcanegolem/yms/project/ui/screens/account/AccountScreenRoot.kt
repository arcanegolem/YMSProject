package arcanegolem.yms.project.ui.screens.account

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
import arcanegolem.yms.project.ui.components.top_bar.ProvideYMSTopAppBarTitle

@Composable
fun AccountScreenRoot(
  navController: NavController,
  viewModel: AccountViewModel
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.account_title)) }

  val state = viewModel.state.collectAsStateWithLifecycle()

  ProvideYMSTopAppBarActions {
    IconButton(
      enabled = state.value is AccountState.Target,
      onClick = {
        val captured = state.value as AccountState.Target

        navController.navigate(
          AccountEdit(captured.result.name, captured.result.balance, captured.result.currency)
        )
      }
    ) { Icon(painter = painterResource(R.drawable.edit), contentDescription = null) }
  }


  AccountScreen(state, viewModel::processEvent)
}
