package io.nemiron.storetk.serialization

import io.nemiron.storetk.common.WrongData
import io.nemiron.storetk.serializer.google.GoogleProtoBufSerializer
import org.junit.Assert.*
import org.junit.Test

class GoogleProtoBufSerializerTests {

    @Test
    fun `serialize primitive type`() {
        val sut = GoogleProtoBufSerializer
        val input = 1337
        val serializedInt = sut.serialize(input)
        val output = sut.deserialize(serializedInt, Int::class)
        assertEquals(input, output)
    }

    @Test
    fun `serialize String class`() {
        val sut = GoogleProtoBufSerializer
        val input = "ISCS EAZY!!!"
        val serializedData = sut.serialize(input)
        val output = sut.deserialize(serializedData, input::class)
        assertEquals(input, output)
    }

    @Test
    fun `serialize collection`() {
        val sut = GoogleProtoBufSerializer
        val input = listOf(1, 3, 3, 7)
        val serializedData = sut.serialize(input)
        val output = sut.deserialize(serializedData, input::class)
        assertEquals(input, output)
    }

    @Test
    fun `fail when serialize non-primitive type`() {
        assertThrows(TypeCastException::class.java) {
            val sut = GoogleProtoBufSerializer
            val wrongInput = WrongData("ISCS EAZY!!!")
            sut.serialize(wrongInput)
        }
    }
}