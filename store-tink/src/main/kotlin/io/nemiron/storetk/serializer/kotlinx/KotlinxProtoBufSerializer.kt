package io.nemiron.storetk.serializer.kotlinx

import io.nemiron.storetk.core.Data
import io.nemiron.storetk.serializer.StoreTkSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class KotlinxProtoBufSerializer(
    private val protoBuf: ProtoBuf = ProtoBuf
) : StoreTkSerializer {

    override fun <T : Any> serialize(value: T): Data {
        @Suppress("UNCHECKED_CAST")
        val classSerializer = value::class.serializer() as KSerializer<T>
        val serializedBytes = protoBuf.encodeToByteArray(classSerializer, value)
        return Data(serializedBytes)
    }

    override fun <T : Any> deserialize(data: Data, kClass: KClass<T>): T {
        val classSerializer = kClass.serializer()
        val serializedByteArray = data.value
        return protoBuf.decodeFromByteArray(classSerializer, serializedByteArray)
    }
}