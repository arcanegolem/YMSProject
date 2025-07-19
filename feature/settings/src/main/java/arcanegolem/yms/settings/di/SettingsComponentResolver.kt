package arcanegolem.yms.settings.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
internal fun resolveDaggerComponent() : SettingsComponent {
  val context = LocalContext.current.applicationContext
  val dependencies = remember {
    (context as SettingsDependenciesProvider).resolveSettingsDependencies()
  }
  return remember(dependencies) {
    DaggerSettingsComponent.builder().settingsDependencies(dependencies).build()
  }
}