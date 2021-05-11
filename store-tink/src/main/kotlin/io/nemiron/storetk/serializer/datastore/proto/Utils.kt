package io.nemiron.storetk.serializer.datastore.proto

import io.nemiron.storetk.serializer.datastore.proto.StoreTkProto.Value
import io.nemiron.storetk.serializer.datastore.proto.StoreTkProto.Value.Type
import io.nemiron.storetk.serializer.datastore.proto.StoreTkProto.Value.Type.*
import kotlin.reflect.KClass

internal fun Any.toProtoValue(): Value =
    if (this is List<*>) {
        Value.newBuilder()
            .also { builder ->
                this.forEach { item ->
                    item
                        ?.let(::getProtoType)
                        ?.let(builder::addMultiValue)
                }
            }
            .build()
    } else {
        Value.newBuilder()
            .setSingleValue(getProtoType(this))
            .build()
    }

internal fun <T : Any> Value.toAnyValue(kClass: KClass<T>): T {
    val anyValue = if (hasSingleValue()) {
        singleValue.toSingleValue()
    } else {
        multiValueList.toListValue()
    }

    @Suppress("UNCHECKED_CAST")
    return (anyValue as? T)
        ?: throw TypeCastException("Cannot transform protobuf bytes to type ${kClass.qualifiedName}")
}

internal fun Type.toSingleValue(): Any =
    when (typeCase) {
        TypeCase.DOUBLE_VALUE -> doubleValue
        TypeCase.FLOAT_VALUE -> floatValue
        TypeCase.INT_VALUE -> intValue
        TypeCase.LONG_VALUE -> longValue
        TypeCase.BOOL_VALUE -> boolValue
        TypeCase.STRING_VALUE -> stringValue
        else -> throw TypeCastException("Protobuf type not supported: $typeCase")
    }

internal fun List<Type>.toListValue(): List<Any> =
    mapNotNull { it.toSingleValue() }

private fun getProtoType(value: Any): Type =
    newBuilder()
        .apply {
            when (value) {
                is Double -> doubleValue = value
                is Float -> floatValue = value
                is Int -> intValue = value
                is Long -> longValue = value
                is Boolean -> boolValue = value
                is String -> stringValue = value
                else -> throw TypeCastException("Protobuf type not supported: ${value::class}")
            }
        }
        .build()