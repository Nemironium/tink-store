package io.nemiron.storetk.encryptor

import io.nemiron.storetk.common.Data
import io.nemiron.storetk.common.EncryptedData

interface StoreTkEncryptor {

    fun encrypt(data: Data): EncryptedData

    fun decrypt(encryptedData: EncryptedData): Data
}