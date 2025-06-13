package arcanegolem.yms.project.categories

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import arcanegolem.yms.project.categories.state_handlers.TargetCategoriesState
import arcanegolem.yms.project.common.state_handlers.ErrorState
import arcanegolem.yms.project.common.state_handlers.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
  viewModel: CategoriesViewModel = koinViewModel()
) {
  val state = viewModel.state.collectAsState()

  when (val s = state.value) {
    CategoriesState.Idle -> viewModel.processEvent(CategoriesEvent.LoadCategories)
    CategoriesState.Loading -> LoadingState()
    is CategoriesState.Target -> TargetCategoriesState(s)
    is CategoriesState.Error -> ErrorState(s.error)
  }
}