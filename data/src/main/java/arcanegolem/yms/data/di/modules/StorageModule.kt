package arcanegolem.yms.data.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import arcanegolem.yms.data.datastore.preferencesDataStore
import dagger.Module
import dagger.Provides

@Module
class StorageModule {
  @Provides
  fun provideDataStorePreferences(context: Context) : DataStore<Preferences> {
    return context.preferencesDataStore
  }
}