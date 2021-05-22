package io.nemiron.storetk.storer.datastore

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import io.nemiron.storetk.core.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.io.OutputStream

private const val DATA_STORE_PREFERENCE_FILE = "data-store-preferences.preferences_pb"
private const val DATA_STORE_SCHEMA_FILE = "data-store"

internal fun providePreferencesDataStore(application: Application): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create {
        File(
            application.filesDir,
            "datastore/$DATA_STORE_PREFERENCE_FILE"
        )
    }
}

internal fun provideSchemaDataStore(application: Application): DataStore<Data> {
    return DataStoreFactory.create(
        produceFile = { File(application.filesDir, "datastore/$DATA_STORE_SCHEMA_FILE") },
        serializer = DataStoreSerializer()
    )
}

private class DataStoreSerializer : Serializer<Data> {

    override val defaultValue = Data(ByteArray(size = 0))

    override suspend fun readFrom(input: InputStream): Data = withContext(Dispatchers.IO) {
        Data(input.readBytes())
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: Data, output: OutputStream) = withContext(Dispatchers.IO) {
        output.write(t.value)
    }
}