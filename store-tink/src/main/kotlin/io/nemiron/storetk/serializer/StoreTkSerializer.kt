package io.nemiron.storetk.serializer

import io.nemiron.storetk.common.Data
import kotlin.reflect.KClass

interface StoreTkSerializer {

    fun <T : Any> serialize(value: T): Data

    fun <T : Any> deserialize(data: Data, kClass: KClass<T>): T
}