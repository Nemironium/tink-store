package io.nemiron.example

import android.app.Application
import io.nemiron.storetk.core.FlowStoreTk
import io.nemiron.storetk.core.Key
import io.nemiron.storetk.encryptor.TinkEncryptor
import io.nemiron.storetk.serializer.google.GoogleProtoBufSerializer
import io.nemiron.storetk.serializer.kotlinx.KotlinxJsonSerializer
import io.nemiron.storetk.storer.datastore.DataStorePreferencesStorer
import io.nemiron.storetk.storer.datastore.DataStoreSchemaStorer
import kotlinx.coroutines.flow.first

class DataManager(
    application: Application
) {

    private val preferencesStorer = FlowStoreTk.init(
        serializer = KotlinxJsonSerializer(),
        encryptor = TinkEncryptor.with(application),
        storer = DataStorePreferencesStorer.with(application)
    )

    private val schemaStorer = FlowStoreTk.init(
        serializer = GoogleProtoBufSerializer,
        encryptor = TinkEncryptor.with(application),
        storer = DataStoreSchemaStorer.with(application)
    )

    companion object {
        private val TEST_DATA_KEY = Key("TestDataKey")
        private val TEST_STRING_KEY = Key("TestStringKey")
    }

    suspend fun getTestData(): TestData? {
        return preferencesStorer.get(TEST_DATA_KEY, TestData::class).first()
    }

    suspend fun saveTestData(testData: TestData) {
        preferencesStorer.set(TEST_DATA_KEY, testData)
    }

    suspend fun saveString(value: String) {
        schemaStorer.set(TEST_STRING_KEY, value)
    }

    suspend fun getString(): String? {
        return schemaStorer.get(TEST_STRING_KEY, String::class).first()
    }
}