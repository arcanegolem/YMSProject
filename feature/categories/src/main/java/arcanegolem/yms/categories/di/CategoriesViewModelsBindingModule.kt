package arcanegolem.yms.categories.di

import androidx.lifecycle.ViewModel
import arcanegolem.yms.categories.ui.categories.CategoriesViewModel
import arcanegolem.yms.core.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CategoriesViewModelsBindingModule {
  @[Binds IntoMap ViewModelKey(CategoriesViewModel::class)]
  fun bindCategoriesViewModelToViewModel(viewModel: CategoriesViewModel) : ViewModel
}