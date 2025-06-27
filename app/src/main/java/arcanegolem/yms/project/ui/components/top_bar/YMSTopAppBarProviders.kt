package arcanegolem.yms.project.ui.components.top_bar

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

/**
 * Функция передачи NavAction в верхний бар
 *
 * @param action [Composable] функция описывающая содержимое NavAction
 */
@Composable
fun ProvideYMSTopAppBarNavAction(action : @Composable () -> Unit) {
  if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
    return
  val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
  SideEffect {
    actionViewModel.navActionState = action
  }
}

/**
 * Функция передачи Actions в верхний бар
 *
 * @param actions [Composable] функция описывающая содержимое Actions
 */
@Composable
fun ProvideYMSTopAppBarActions(actions: @Composable RowScope.() -> Unit) {
  if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
    return
  val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
  SideEffect {
    actionViewModel.actionState = actions
  }
}

/**
 * Функция передачи заголовка в верхний бар
 *
 * @param title [Composable] функция описыввающая содержимое заголовка
 */
@Composable
fun ProvideYMSTopAppBarTitle(title: @Composable () -> Unit) {
  if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
    return
  val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
  SideEffect {
    actionViewModel.titleState = title
  }
}

/**
 * Функция получения NavAction для верхнего бара
 *
 * @param backStackEntry текущий верхний элемент стека навигации
 */
@Composable
fun YMSTopAppBarNavAction(backStackEntry: NavBackStackEntry?) {
  val stateHolder = rememberSaveableStateHolder()
  backStackEntry?.LocalOwnersProvider(stateHolder) {
    val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
    actionViewModel.navActionState?.let { content -> content() }
  }
}

/**
 * Функция получения Actions для верхнего бара
 *
 * @param backStackEntry текущий верхний элемент стека навигации
 */
@Composable
fun RowScope.YMSTopAppBarActions(backStackEntry: NavBackStackEntry?) {
  val stateHolder = rememberSaveableStateHolder()
  backStackEntry?.LocalOwnersProvider(stateHolder) {
    val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
    actionViewModel.actionState?.let { content -> content() }
  }
}

/**
 * Функция получения заголоввка для верхнего бара
 *
 * @param backStackEntry текущий верхний элемент стека навигации
 */
@Composable
fun YMSTopAppBarTitle(backStackEntry: NavBackStackEntry?) {
  val stateHolder = rememberSaveableStateHolder()
  backStackEntry?.LocalOwnersProvider(stateHolder) {
    val actionViewModel = viewModel(initializer = { YMSTopAppBarViewModel() })
    actionViewModel.titleState?.let { content -> content() }
  }
}

/**
 * ViewModel для верхнего бара
 *
 * @property titleState текущий заголовок верхнего бара
 * @property navActionState текущий NavAction верхнего бара
 * @property actionState текущий набор Actions верхнего бара
 */
private class YMSTopAppBarViewModel : ViewModel() {
  var titleState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
  var navActionState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
  var actionState by mutableStateOf(null as (@Composable RowScope.() -> Unit)?, referentialEqualityPolicy())
}
