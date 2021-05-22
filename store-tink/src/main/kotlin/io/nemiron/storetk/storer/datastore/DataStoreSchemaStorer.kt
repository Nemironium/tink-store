package io.nemiron.storetk.storer.datastore

import android.app.Application
import androidx.datastore.core.DataStore
import io.nemiron.storetk.core.Data
import io.nemiron.storetk.core.Key
import io.nemiron.storetk.storer.StoreTkStorer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class DataStoreSchemaStorer private constructor(
    private val dataStore: DataStore<Data>
) : StoreTkStorer {

    /*
    * TODO: make this storer unique for each data type (with generics and probably reflection)
    *  or add map with keys that should be also stored on the disk
    * */
    companion object {

        fun with(application: Application): StoreTkStorer =
            DataStoreSchemaStorer(provideSchemaDataStore(application))
    }

    override suspend fun saveData(key: Key, data: Data) {
        dataStore.updateData {
            Data(it.value.copyInto(data.value))
        }
    }

    override suspend fun getData(key: Key): Flow<Data> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(Data(ByteArray(size = 0)))
            } else {
                throw e
            }
        }
    }

    override suspend fun removeData(key: Key) {
        dataStore.updateData {
            it.copy(value = ByteArray(size = 0))
        }
    }

    override suspend fun clear() {
        dataStore.updateData {
            Data(ByteArray(size = 0))
        }
    }
}