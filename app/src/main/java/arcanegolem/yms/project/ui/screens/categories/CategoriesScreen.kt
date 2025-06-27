package arcanegolem.yms.project.ui.screens.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.project.ui.screens.categories.state_handlers.TargetCategoriesState
import arcanegolem.yms.project.ui.components.state_handlers.error.ErrorState
import arcanegolem.yms.project.ui.components.state_handlers.LoadingState

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
