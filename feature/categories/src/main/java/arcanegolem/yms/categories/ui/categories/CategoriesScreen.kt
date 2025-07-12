package arcanegolem.yms.categories.ui.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.categories.ui.categories.state_handlers.TargetCategoriesState
import arcanegolem.yms.core.ui.components.state_handlers.LoadingState
import arcanegolem.yms.core.ui.components.state_handlers.error.ErrorState

@Composable
fun CategoriesScreen(
  state : State<CategoriesState>,
  eventProcessor : (CategoriesEvent) -> Unit
) {
  when (val s = state.value) {
    CategoriesState.Idle -> eventProcessor(CategoriesEvent.LoadCategories)
    CategoriesState.Loading -> LoadingState()
    is CategoriesState.Target -> TargetCategoriesState(s)
    is CategoriesState.Error -> ErrorState(s.error)
  }
}
