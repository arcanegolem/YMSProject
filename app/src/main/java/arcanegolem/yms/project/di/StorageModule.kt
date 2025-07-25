package arcanegolem.yms.project.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import arcanegolem.yms.core.data.database.YMSDatabase
import arcanegolem.yms.core.data.datastore.preferencesDataStore
import arcanegolem.yms.core.di.AppVersionQualifier
import arcanegolem.yms.settings.di.BuildInfo
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

  @[ApplicationScope Provides]
  fun provideDataStoreManager(context : Context) : DataStore<Preferences> {
    return context.preferencesDataStore
  }
  
  @[ApplicationScope Provides]
  fun provideBuildInfo(@AppVersionQualifier appVersion : String) : BuildInfo {
    return BuildInfo(appVersion)
  }

  @[ApplicationScope Provides]
  fun provideDatabase(context: Context) : YMSDatabase {
    return YMSDatabase.getInstance(context)
  }
}