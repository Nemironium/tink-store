package io.nemiron.storetk.storer

import io.nemiron.storetk.core.Data
import io.nemiron.storetk.core.Key
import kotlinx.coroutines.flow.Flow

interface StoreTkStorer {

    suspend fun saveData(key: Key, data: Data)

    suspend fun getData(key: Key): Flow<Data>

    suspend fun removeData(key: Key)

    suspend fun clear()
}