package io.nemiron.storetk.common

import kotlinx.serialization.Serializable

@Serializable
data class TestData(val value: String)

data class WrongData(val value: String)