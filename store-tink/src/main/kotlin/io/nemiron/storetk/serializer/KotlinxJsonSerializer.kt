package io.nemiron.storetk.serializer

import io.nemiron.storetk.common.Data
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class KotlinxJsonSerializer(
    private val json: Json = Json
) : StoreTkSerializer {

    override fun <T : Any> serialize(value: T): Data {
        val classSerializer = value::class.serializer()
        val serializedBytes = json.encodeToString(classSerializer, value).encodeToByteArray()
        serializedBytes.decodeToString()

        return Data(serializedBytes)
    }

    override fun <T : Any> deserialize(data: Data, kClass: KClass<T>): T {
        val classSerializer = kClass.serializer()
        val serializedJsonString = data.value.decodeToString()
        return json.decodeFromString(classSerializer, serializedJsonString)
    }
}



