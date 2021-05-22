package io.nemiron.storetk.serialization

import io.nemiron.storetk.common.TestData
import io.nemiron.storetk.common.WrongData
import io.nemiron.storetk.serializer.kotlinx.KotlinxJsonSerializer
import kotlinx.serialization.SerializationException
import org.junit.Assert.*
import org.junit.Test

class KotlinxJsonSerializerTests {
    @Test
    fun `serialize class with serializable annotation`() {
        val sut = KotlinxJsonSerializer()
        val input = TestData("ISCS EAZY!!!")
        val serializedData = sut.serialize(input)
        val output = sut.deserialize(serializedData, TestData::class)
        assertEquals(input.value, output.value)
    }

    @Test
    fun `fail when class doesn't have serializable annotation`() {
        assertThrows(SerializationException::class.java) {
            val sut = KotlinxJsonSerializer()
            val wrongInput = WrongData("ISCS EAZY!!!")
            sut.serialize(wrongInput)
        }
    }

    @Test
    fun `serialize primitive type`() {
        val sut = KotlinxJsonSerializer()
        val input = 1337
        val serializedInt = sut.serialize(input)
        val output = sut.deserialize(serializedInt, Int::class)
        assertEquals(input, output)
    }
}