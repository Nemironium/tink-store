package io.nemiron.storetk.serializer.kotlinx

import io.nemiron.storetk.core.Data
import io.nemiron.storetk.serializer.StoreTkSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class KotlinxJsonSerializer(
    private val json: Json = Json
) : StoreTkSerializer {

    override fun <T : Any> serialize(value: T): Data {
        @Suppress("UNCHECKED_CAST")
        val classSerializer = value::class.serializer() as KSerializer<T>
        val serializedBytes = json.encodeToString(classSerializer, value).encodeToByteArray()
        return Data(serializedBytes)
    }

    override fun <T : Any> deserialize(data: Data, kClass: KClass<T>): T {
        val classSerializer = kClass.serializer()
        val serializedJsonString = data.value.decodeToString()
        return json.decodeFromString(classSerializer, serializedJsonString)
    }
}



