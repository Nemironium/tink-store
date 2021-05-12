package io.nemiron.storetk.encryptor

import android.app.Application
import com.google.crypto.tink.Aead
import io.nemiron.storetk.common.Data
import io.nemiron.storetk.common.EncryptedData

class TinkEncryptor private constructor(
    private val aead: Aead,
    private val associatedData: ByteArray
) : StoreTkEncryptor {

    companion object {
        private val DEFAULT_ASSOCIATED_DATA = ByteArray(size = 0)

        fun with(
            application: Application,
            associatedData: ByteArray = DEFAULT_ASSOCIATED_DATA
        ): StoreTkEncryptor =
            TinkEncryptor(provideAead(application), associatedData)
    }

    override fun encrypt(data: Data): EncryptedData {
        val encryptedByteArray = aead.encrypt(data.value, associatedData)
        return EncryptedData(encryptedByteArray)
    }

    override fun decrypt(encryptedData: EncryptedData): Data {
        val decryptedByteArray = aead.decrypt(encryptedData.value, associatedData)
        return Data(decryptedByteArray)
    }
}