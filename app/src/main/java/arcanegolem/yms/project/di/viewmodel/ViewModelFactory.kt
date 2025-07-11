package arcanegolem.yms.project.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arcanegolem.yms.project.di.annotations.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@ApplicationScope
class ViewModelFactory @Inject constructor(
  private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    val viewModelProvider = viewModels[modelClass]
      ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")

    try {
      @Suppress("UNCHECKED_CAST")
      return viewModelProvider.get() as T
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }
}