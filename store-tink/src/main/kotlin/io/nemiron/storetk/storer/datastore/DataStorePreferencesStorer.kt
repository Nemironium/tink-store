package io.nemiron.storetk.storer.datastore

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import io.nemiron.storetk.core.Data
import io.nemiron.storetk.core.Key
import io.nemiron.storetk.storer.StoreTkStorer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStorePreferencesStorer private constructor(
    private val dataStore: DataStore<Preferences>
) : StoreTkStorer {

    companion object {

        fun with(
            application: Application
        ): StoreTkStorer =
            DataStorePreferencesStorer(providePreferencesDataStore(application))
    }

    override suspend fun saveData(key: Key, data: Data) {
        dataStore.edit { preferences ->
            val preferencesKey = stringPreferencesKey(key.value)
            preferences[preferencesKey] = data.value.toString(Charsets.ISO_8859_1)
        }
    }

    override suspend fun getData(key: Key): Flow<Data> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { preferences ->
            val preferencesKey = stringPreferencesKey(key.value)
            val stringData = preferences[preferencesKey] ?: ""
            Data(stringData.toByteArray(Charsets.ISO_8859_1))
        }.map {
            it
        }
    }

    override suspend fun removeData(key: Key) {
        dataStore.edit { preferences ->
            val preferencesKey = stringPreferencesKey(key.value)
            preferences.minusAssign(preferencesKey)
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}