package arcanegolem.yms.project.account

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import arcanegolem.yms.project.R
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun AccountScreenRoot(
  navController: NavController,
  viewModel: AccountViewModel = koinViewModel()
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.account_title)) }
  ProvideYMSTopAppBarActions { IconButton(onClick = {}) { Icon(painter = painterResource(R.drawable.edit), contentDescription = null) } }

  val state = viewModel.state.collectAsStateWithLifecycle()

  AccountScreen(state, viewModel::processEvent)
}