package io.nemiron.storetk.serializer.datastore

import io.nemiron.storetk.common.Data
import io.nemiron.storetk.serializer.StoreTkSerializer
import io.nemiron.storetk.serializer.datastore.proto.StoreTkProto.*
import io.nemiron.storetk.serializer.datastore.proto.toAnyValue
import io.nemiron.storetk.serializer.datastore.proto.toProtoValue
import kotlin.reflect.KClass

/*
* TODO: allow serialize Any class with Any fields
* */
class DataStoreProtoBufSerializer : StoreTkSerializer {

    override fun <T : Any> serialize(value: T): Data {
        val serializedBytes = StoreTk
            .newBuilder()
            .mergeValue(value.toProtoValue())
            .build()
            .toByteArray()

        return Data(serializedBytes)
    }

    override fun <T : Any> deserialize(data: Data, kClass: KClass<T>): T {
        return StoreTk
            .parseFrom(data.value)
            .value
            .toAnyValue(kClass)
    }
}