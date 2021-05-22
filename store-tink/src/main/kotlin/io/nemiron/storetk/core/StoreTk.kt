package io.nemiron.storetk.core

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

interface StoreTk {

    suspend fun <T: Any> set(key: Key, value: T)

    suspend fun <T: Any> get(key: Key, kClass: KClass<T>): Flow<T?>

    suspend fun remove(key: Key)

    suspend fun clear()
}