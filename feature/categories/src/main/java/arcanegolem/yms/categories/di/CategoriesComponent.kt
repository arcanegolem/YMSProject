package arcanegolem.yms.categories.di

import arcanegolem.yms.core.di.ViewModelFactory
import dagger.Component

@CategoriesScope
@Component(
  modules = [CategoriesViewModelsBindingModule::class],
  dependencies = [CategoriesDependencies::class]
)
interface CategoriesComponent {
  fun viewModelFactory() : ViewModelFactory
}