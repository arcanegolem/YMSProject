package arcanegolem.yms.project.top_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.LocalOwnersProvider

@Composable
fun ProvideYMSTopAppBarNavAction(action : @Composable () -> Unit) {
  if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
    return
  val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
  SideEffect {
    actionViewModel.navActionState = action
  }
}

@Composable
fun ProvideYMSTopAppBarActions(actions: @Composable RowScope.() -> Unit) {
  if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
    return
  val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
  SideEffect {
    actionViewModel.actionState = actions
  }
}

@Composable
fun ProvideYMSTopAppBarTitle(title: @Composable () -> Unit) {
  if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
    return
  val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
  SideEffect {
    actionViewModel.titleState = title
  }
}

@Composable
fun YMSTopAppBarNavAction(backStackEntry: NavBackStackEntry?) {
  val stateHolder = rememberSaveableStateHolder()
  backStackEntry?.LocalOwnersProvider(stateHolder) {
    val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
    actionViewModel.navActionState?.let { content -> content() }
  }
}

@Composable
fun RowScope.YMSTopAppBarActions(backStackEntry: NavBackStackEntry?) {
  val stateHolder = rememberSaveableStateHolder()
  backStackEntry?.LocalOwnersProvider(stateHolder) {
    val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
    actionViewModel.actionState?.let { content -> content() }
  }
}


@Composable
fun YMSTopAppBarTitle(backStackEntry: NavBackStackEntry?) {
  val stateHolder = rememberSaveableStateHolder()
  backStackEntry?.LocalOwnersProvider(stateHolder) {
    val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
    actionViewModel.titleState?.let { content -> content() }
  }
}


private class YMSTopAppBarViewModel : ViewModel() {
  var titleState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
  var navActionState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
  var actionState by mutableStateOf(null as (@Composable RowScope.() -> Unit)?, referentialEqualityPolicy())
}
