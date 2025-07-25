package arcanegolem.yms.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
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
    private val DARK_THEME_FLAG_KEY = booleanPreferencesKey("DARK_THEME_FLAG")
    private val PRIMARY_COLOR_KEY = longPreferencesKey("PRIMARY_COLOR")
    private val SECONDARY_COLOR_KEY = longPreferencesKey("SECONDARY_COLOR")
    private val TAB_HAPTIC_ENABLED_KEY = booleanPreferencesKey("TAB_HAPTIC_ENABLED")
    private val TAB_HAPTIC_PATTERN_KEY = stringPreferencesKey("TAB_HAPTIC_PATTERN")
  }
  
  fun getHapticPattern(defaultValue : LongArray) : Flow<LongArray> {
    return dataStore.data.map { preferences ->
      preferences[TAB_HAPTIC_PATTERN_KEY]?.let { Json.decodeFromString<LongArray>(it) }
        ?: defaultValue
    }
  }
  
  suspend fun setHapticPattern(pattern : LongArray) {
    dataStore.edit { preferences ->
      preferences[TAB_HAPTIC_PATTERN_KEY] = Json.encodeToString(pattern)
    }
  }
  
  fun getHapticEnabled() : Flow<Boolean> {
    return dataStore.data.map { preferences ->
      preferences[TAB_HAPTIC_ENABLED_KEY] ?: false
    }
  }
  
  suspend fun setHapticEnabled(value : Boolean) {
    dataStore.edit { preferences ->
      preferences[TAB_HAPTIC_ENABLED_KEY] = value
    }
  }

  fun getPrimaryColor(defaultValue : Long) : Flow<Long> {
    return dataStore.data.map { preferences ->
      preferences[PRIMARY_COLOR_KEY] ?: defaultValue
    }
  }
  
  suspend fun setPrimaryColor(value : Long) {
    dataStore.edit { preferences ->
      preferences[PRIMARY_COLOR_KEY] = value
    }
  }
  
  fun getSecondaryColor(defaultValue: Long) : Flow<Long> {
    return dataStore.data.map { preferences ->
      preferences[SECONDARY_COLOR_KEY] ?: defaultValue
    }
  }
  
  suspend fun setSecondaryColor(value : Long) {
    dataStore.edit { preferences ->
      preferences[SECONDARY_COLOR_KEY] = value
    }
  }
  
  fun getDarkThemeFlagFlow() : Flow<Boolean> {
    return dataStore.data.map { preferences ->
      preferences[DARK_THEME_FLAG_KEY] ?: false
    }
  }
  
  suspend fun setDarkTheme(value : Boolean) {
    dataStore.edit { preferences ->
      preferences[DARK_THEME_FLAG_KEY] = value
    }
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
