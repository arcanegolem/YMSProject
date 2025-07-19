package arcanegolem.yms.settings.ui.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarTitle
import arcanegolem.yms.settings.di.resolveDaggerComponent

@Composable
fun SettingsScreenRoot(
  navController: NavController,
  viewModel: SettingsViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory())
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.settings_title)) }

  val state = viewModel.state.collectAsStateWithLifecycle()

  SettingsScreen(state, viewModel::processEvent)
}
