package arcanegolem.yms.project

import android.app.Application
import android.content.Context
import arcanegolem.yms.data.BuildConfig
import arcanegolem.yms.project.di.ApplicationComponent
import arcanegolem.yms.project.di.DaggerApplicationComponent

class YMSProjectApplication : Application() {

  val applicationComponent : ApplicationComponent by lazy {
    DaggerApplicationComponent.builder()
      .context(this)
      .token(BuildConfig.TOKEN)
      .build()
  }
}

val Context.applicationComponent : ApplicationComponent
  get() = when(this) {
    is YMSProjectApplication -> applicationComponent
    else -> this.applicationContext.applicationComponent
  }
