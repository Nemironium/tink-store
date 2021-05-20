package io.nemiron.storetk.encryptor

interface StoreTkEncryptor {

    fun encrypt(value: ByteArray): ByteArray

    fun decrypt(encryptedValue: ByteArray): ByteArray
}