package arcanegolem.yms.project

import android.app.Application
import arcanegolem.yms.data.di.dataModule
import arcanegolem.yms.project.di.appModule
import arcanegolem.yms.project.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YMSProjectApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@YMSProjectApplication)
      modules(
        appModule,
        domainModule,
        dataModule
      )
    }
  }
}