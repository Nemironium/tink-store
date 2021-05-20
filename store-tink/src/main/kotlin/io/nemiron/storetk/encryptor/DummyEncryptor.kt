package io.nemiron.storetk.encryptor

object DummyEncryptor : StoreTkEncryptor {
    override fun encrypt(value: ByteArray) = value

    override fun decrypt(encryptedValue: ByteArray) = encryptedValue
}