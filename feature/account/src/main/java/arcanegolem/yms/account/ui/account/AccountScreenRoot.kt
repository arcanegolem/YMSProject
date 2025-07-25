package arcanegolem.yms.account.ui.account

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
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarTitle

@Composable
fun AccountScreenRoot(
  navController: NavController,
  viewModel: AccountViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory())
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.account_title)) }

  val state = viewModel.state.collectAsStateWithLifecycle()

  ProvideYMSTopAppBarActions {
    IconButton(
      enabled = state.value is AccountState.Target,
      onClick = {
        val captured = state.value as AccountState.Target

        navController.navigate(
          AccountEdit(captured.accountModel.name, captured.accountModel.balance, captured.accountModel.currency)
        )
      }
    ) { Icon(painter = painterResource(R.drawable.edit), contentDescription = null) }
  }


  AccountScreen(state, viewModel::processEvent)
}
