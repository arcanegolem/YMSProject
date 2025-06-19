package arcanegolem.yms.project.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import arcanegolem.yms.project.categories.state_handlers.TargetCategoriesState
import arcanegolem.yms.project.common.state_handlers.error.ErrorState
import arcanegolem.yms.project.common.state_handlers.LoadingState

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