package io.nemiron.storetk.serializer

import io.nemiron.storetk.common.Data
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class KotlinxCborSerializer(
    private val cbor: Cbor = Cbor
) : StoreTkSerializer {

    override fun <T : Any> serialize(value: T): Data {
        @Suppress("UNCHECKED_CAST")
        val classSerializer = value::class.serializer() as KSerializer<T>
        val serializedBytes = cbor.encodeToByteArray(classSerializer, value)
        return Data(serializedBytes)
    }

    override fun <T : Any> deserialize(data: Data, kClass: KClass<T>): T {
        val classSerializer = kClass.serializer()
        val serializedByteArray = data.value
        return cbor.decodeFromByteArray(classSerializer, serializedByteArray)
    }
}