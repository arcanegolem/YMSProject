package arcanegolem.yms.account.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
internal fun resolveDaggerComponent() : AccountComponent {
  val context = LocalContext.current.applicationContext
  val dependencies = remember {
    (context as AccountDependenciesProvider).resolveAccountDependencies()
  }
  return remember(dependencies) {
    DaggerAccountComponent.builder().accountDependencies(dependencies).build()
  }
}