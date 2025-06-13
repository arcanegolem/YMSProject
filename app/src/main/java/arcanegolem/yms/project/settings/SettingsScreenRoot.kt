package arcanegolem.yms.project.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import arcanegolem.yms.project.R
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarTitle

@Composable
fun SettingsScreenRoot() {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.settings_title)) }

  SettingsScreen()
}