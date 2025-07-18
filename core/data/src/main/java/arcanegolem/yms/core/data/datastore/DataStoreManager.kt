package arcanegolem.yms.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import arcanegolem.yms.core.data.datastore.models.AccountInfoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

val Context.preferencesDataStore : DataStore<Preferences> by preferencesDataStore(name = "preferences")

/**
 * Хелпер-класс для взаимодействия с DataStore с информацией об активном аккаунте
 *
 * @param dataStore объект Preferences [DataStore]
 */
class DataStoreManager @Inject constructor(
  private val dataStore: DataStore<Preferences>
) {
  companion object {
    private val ACTIVE_ACCOUNT_KEY = stringPreferencesKey("ACTIVE_ACCOUNT")
    private val LAST_SYNC_KEY = stringPreferencesKey("LAST_SYNC")
  }

  fun getLastSyncFlow() : Flow<String> {
    return dataStore.data.map { preferences ->
      preferences[LAST_SYNC_KEY] ?: ""
    }
  }

  suspend fun updateLastSync(value : String) {
    dataStore.edit { preferences ->
      preferences[LAST_SYNC_KEY] = value
    }
  }

  suspend fun getActiveAccount() : AccountInfoModel? {
    return dataStore.data.first()[ACTIVE_ACCOUNT_KEY]?.let { Json.decodeFromString(it) }
  }

  fun getActiveAccountFlow() : Flow<AccountInfoModel?> {
    return dataStore.data.map { preferences ->
      preferences[ACTIVE_ACCOUNT_KEY]?.let { Json.decodeFromString(it) }
    }
  }

  suspend fun updateActiveAccount(account : AccountInfoModel) {
    dataStore.edit { preferences ->
      preferences[ACTIVE_ACCOUNT_KEY] = Json.encodeToString(account)
    }
  }
}
