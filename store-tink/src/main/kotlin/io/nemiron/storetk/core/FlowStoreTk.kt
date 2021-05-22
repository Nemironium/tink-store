package io.nemiron.storetk.core

import android.app.Application
import io.nemiron.storetk.encryptor.StoreTkEncryptor
import io.nemiron.storetk.encryptor.TinkEncryptor
import io.nemiron.storetk.serializer.StoreTkSerializer
import io.nemiron.storetk.serializer.kotlinx.KotlinxJsonSerializer
import io.nemiron.storetk.storer.StoreTkStorer
import io.nemiron.storetk.storer.datastore.DataStorePreferencesStorer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KClass

class FlowStoreTk private constructor(
    private val serializer: StoreTkSerializer,
    private val encryptor: StoreTkEncryptor,
    private val storer: StoreTkStorer
) : StoreTk {

    companion object {

        fun init(
            serializer: StoreTkSerializer,
            encryptor: StoreTkEncryptor,
            storer: StoreTkStorer
        ): StoreTk = FlowStoreTk(serializer, encryptor, storer)

        fun init(
            application: Application
        ): StoreTk {
            return FlowStoreTk(
                serializer = KotlinxJsonSerializer(),
                encryptor = TinkEncryptor.with(application),
                storer = DataStorePreferencesStorer.with(application)
            )
        }
    }

    override suspend fun <T : Any> set(key: Key, value: T) {
        val serializedData = serializer.serialize(value)
        val encryptedValue = encryptor.encrypt(serializedData.value)
        val encryptedData = Data(encryptedValue)
        storer.saveData(key, encryptedData)
    }

    override suspend fun <T : Any> get(key: Key, kClass: KClass<T>): Flow<T?> {
        return storer.getData(key)
            .map { encryptedData ->
                if (encryptedData.isEmpty()) {
                    null
                } else {
                    val decryptedValue = encryptor.decrypt(encryptedData.value)
                    serializer.deserialize(Data(decryptedValue), kClass)
                }
            }
    }

    override suspend fun remove(key: Key) {
        storer.removeData(key)
    }

    override suspend fun clear() {
        storer.clear()
    }
}