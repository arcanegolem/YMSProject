package arcanegolem.yms.project.di

import android.content.Context
import arcanegolem.yms.data.di.annotations.TokenQualifier
import arcanegolem.yms.data.di.modules.DataModule
import arcanegolem.yms.project.MainActivity
import arcanegolem.yms.project.di.annotations.ApplicationScope
import arcanegolem.yms.project.di.modules.ViewModelBindingModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
  modules = [ViewModelBindingModule::class, DataModule::class]
)
interface ApplicationComponent  {

  fun inject(activity: MainActivity)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context : Context) : Builder

    @BindsInstance
    fun token(@TokenQualifier token : String) : Builder

    fun build() : ApplicationComponent
  }
}