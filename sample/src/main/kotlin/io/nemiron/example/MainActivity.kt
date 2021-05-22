package io.nemiron.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "STORE_KT"
    }

    private val dataManager: DataManager by lazy { DataManager(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenCreated {
            val testData = TestData("Ибкс -- эт окруто!!!")
            dataManager.saveTestData(testData)
            val retrievedTestData = dataManager.getTestData()

            Log.i(TAG, "testData = $testData")
            Log.i(TAG, "retrievedTestData = $retrievedTestData")

            val testString = "ISCS EAZY!!!ISCS EAZY!!!ISCS EAZY!!!"
            dataManager.saveString(testString)
            val retrievedTestString = dataManager.getString()

            Log.i(TAG, "testString = $testString")
            Log.i(TAG, "retrievedTestString = $retrievedTestString")
        }
    }
}