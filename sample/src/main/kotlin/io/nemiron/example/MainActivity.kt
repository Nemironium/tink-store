package io.nemiron.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.nemiron.storetk.serializer.KotlinxCborSerializer
import io.nemiron.storetk.serializer.KotlinxJsonSerializer
import io.nemiron.storetk.serializer.KotlinxProtoBufSerializer
import kotlinx.serialization.Serializable

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "STORE_KT"
    }

    @Serializable
    data class TestData(val value: String)
    
    data class WrongData(val value: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*testJsonSerializableClass()
        testJsonIntData()
        testJsonNonSerializableClass()*/
        /*testProtoBufSerializableClass()
        testProtoBufIntData()
        testProtoBufNonSerializableClass()*/
        testCborSerializableClass()
        testCborIntData()
        testCborNonSerializableClass()
    }

    private fun testJsonSerializableClass() {
        val serializer = KotlinxJsonSerializer()
        val testData = TestData("ISCS EAZY!!!")
        val serializedData = serializer.serialize(testData)
        val deserializedData = serializer.deserialize(serializedData, TestData::class)

        Log.i(TAG, "testData = $testData")
        Log.i(TAG, "serializedData = $serializedData")
        Log.i(TAG, "deserializedData = $deserializedData")
    }

    private fun testJsonNonSerializableClass() {
        val serializer = KotlinxJsonSerializer()
        try {
            val wrongData = WrongData("ISCS EAZY!!!")
            serializer.serialize(wrongData)
        } catch (e: Exception) {
            Log.e(TAG, "exception: $e")
        }
    }

    private fun testJsonIntData() {
        val serializer = KotlinxJsonSerializer()
        val testInt = 1337
        val serializedInt = serializer.serialize(testInt)
        val deserializedInt = serializer.deserialize(serializedInt, Int::class)

        Log.i(TAG, "testInt = $testInt")
        Log.i(TAG, "serializedInt = $serializedInt")
        Log.i(TAG, "deserializedInt = $deserializedInt")
    }

    private fun testProtoBufSerializableClass() {
        val serializer = KotlinxProtoBufSerializer()
        val testData = TestData("ISCS EAZY!!!")
        val serializedData = serializer.serialize(testData)
        val deserializedData = serializer.deserialize(serializedData, TestData::class)

        Log.i(TAG, "testData = $testData")
        Log.i(TAG, "serializedData = $serializedData")
        Log.i(TAG, "deserializedData = $deserializedData")
    }

    private fun testProtoBufNonSerializableClass() {
        val serializer = KotlinxProtoBufSerializer()
        try {
            val wrongData = WrongData("ISCS EAZY!!!")
            serializer.serialize(wrongData)
        } catch (e: Exception) {
            Log.e(TAG, "exception: $e")
        }
    }

    private fun testProtoBufIntData() {
        val serializer = KotlinxCborSerializer()
        val testInt = 1337
        val serializedInt = serializer.serialize(testInt)
        val deserializedInt = serializer.deserialize(serializedInt, Int::class)

        Log.i(TAG, "testInt = $testInt")
        Log.i(TAG, "serializedInt = $serializedInt")
        Log.i(TAG, "deserializedInt = $deserializedInt")
    }

    private fun testCborSerializableClass() {
        val serializer = KotlinxCborSerializer()
        val testData = TestData("ISCS EAZY!!!")
        val serializedData = serializer.serialize(testData)
        val deserializedData = serializer.deserialize(serializedData, TestData::class)

        Log.i(TAG, "testData = $testData")
        Log.i(TAG, "serializedData = $serializedData")
        Log.i(TAG, "deserializedData = $deserializedData")
    }

    private fun testCborNonSerializableClass() {
        val serializer = KotlinxCborSerializer()
        try {
            val wrongData = WrongData("ISCS EAZY!!!")
            serializer.serialize(wrongData)
        } catch (e: Exception) {
            Log.e(TAG, "exception: $e")
        }
    }

    private fun testCborIntData() {
        val serializer = KotlinxCborSerializer()
        val testInt = 1337
        val serializedInt = serializer.serialize(testInt)
        val deserializedInt = serializer.deserialize(serializedInt, Int::class)

        Log.i(TAG, "testInt = $testInt")
        Log.i(TAG, "serializedInt = $serializedInt")
        Log.i(TAG, "deserializedInt = $deserializedInt")
    }
}