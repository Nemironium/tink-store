package io.nemiron.storetk.serializer

import kotlinx.serialization.KSerializer
import java.lang.reflect.Modifier

// From Kotlinx.Serialization (Apache 2.0 license)
internal val <T : Any> Class<T>.serializer: KSerializer<T>?
    get() {
        // Check it is an object without using kotlin-reflect
        val field = declaredFields.singleOrNull {
            it.name == "INSTANCE" && it.type == this && Modifier.isStatic(it.modifiers)
        }
            ?: return null
        // Retrieve its instance and call serializer()
        val instance = field.get(null)
        val method =
            methods.singleOrNull { it.name == "serializer" && it.parameterTypes.isEmpty() && it.returnType == KSerializer::class.java }
                ?: return null
        val result = method.invoke(instance)
        @Suppress("UNCHECKED_CAST")
        return result as? KSerializer<T>
    }