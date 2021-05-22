package io.nemiron.storetk.storer

import io.nemiron.storetk.core.Data
import io.nemiron.storetk.core.Key
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

object InMemoryStorer : StoreTkStorer {

    private val storageStateFlow = MutableStateFlow<Map<Key, Data>>(emptyMap())

    override suspend fun saveData(key: Key, data: Data) {
        storageStateFlow.value = storageStateFlow.value.toMutableMap().apply {
            put(key, data)
        }
    }

    override suspend fun getData(key: Key): Flow<Data> = storageStateFlow.map {
        it.getValue(key)
    }

    override suspend fun removeData(key: Key) {
        storageStateFlow.value = storageStateFlow.value.toMutableMap().apply {
            remove(key)
        }
    }

    override suspend fun clear() {
        storageStateFlow.value = emptyMap()
    }
}