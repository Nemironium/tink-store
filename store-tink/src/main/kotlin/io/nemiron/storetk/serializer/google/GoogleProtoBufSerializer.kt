package io.nemiron.storetk.serializer.google

import com.google.protobuf.ExperimentalApi
import io.nemiron.storetk.core.Data
import io.nemiron.storetk.serializer.StoreTkSerializer
import io.nemiron.storetk.serializer.google.proto.StoreTkData
import io.nemiron.storetk.serializer.google.proto.toAnyValue
import io.nemiron.storetk.serializer.google.proto.toProtoValue
import kotlin.reflect.KClass

/*
* TODO: allow serialize Any class with Any fields
* */
@ExperimentalApi("Currently doesn't allow serialize Any class with Any fields")
object GoogleProtoBufSerializer : StoreTkSerializer {

    override fun <T : Any> serialize(value: T): Data {
        val serializedBytes = StoreTkData.Data
            .newBuilder()
            .mergeValue(value.toProtoValue())
            .build()
            .toByteArray()

        return Data(serializedBytes)
    }

    override fun <T : Any> deserialize(data: Data, kClass: KClass<T>): T {
        return StoreTkData.Data
            .parseFrom(data.value)
            .value
            .toAnyValue(kClass)
    }
}