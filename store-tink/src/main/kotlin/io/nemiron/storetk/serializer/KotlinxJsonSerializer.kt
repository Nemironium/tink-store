package io.nemiron.storetk.serializer

import io.nemiron.storetk.common.Data
import kotlinx.serialization.json.Json
import kotlin.reflect.KClass

class KotlinxJsonSerializer(
    private val json: Json = Json
) : StoreTkSerializer {

    override fun <T : Any> serialize(value: T): Data {
        val classSerializer = value.javaClass.serializer
        requireNotNull(classSerializer) {
            "Class must have @Serializable annotation"
        }

        val serializedBytes = json.encodeToString(classSerializer, value).encodeToByteArray()
        serializedBytes.decodeToString()

        return Data(serializedBytes)
    }

    override fun <T : Any> deserialize(data: Data, kClass: KClass<T>): T {
        //TODO: see kClass.serializer()
        val classSerializer = kClass.java.serializer
        requireNotNull(classSerializer) {
            "Class must have @Serializable annotation"
        }
        val serializedJsonString = data.value.decodeToString()

        return json.decodeFromString(classSerializer, serializedJsonString)
    }
}



