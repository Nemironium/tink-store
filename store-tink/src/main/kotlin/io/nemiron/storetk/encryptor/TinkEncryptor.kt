package io.nemiron.storetk.encryptor

import android.app.Application
import com.google.crypto.tink.Aead

class TinkEncryptor private constructor(
    private val aead: Aead,
    private val associatedData: ByteArray
) : StoreTkEncryptor {

    companion object {
        private val DEFAULT_ASSOCIATED_DATA = ByteArray(size = 0)

        fun with(
            application: Application,
            associatedData: ByteArray = DEFAULT_ASSOCIATED_DATA
        ): StoreTkEncryptor {
            return TinkEncryptor(provideAead(application), associatedData)
        }
    }

    override fun encrypt(value: ByteArray): ByteArray =
        aead.encrypt(value, associatedData)

    override fun decrypt(encryptedValue: ByteArray): ByteArray =
        aead.decrypt(encryptedValue, associatedData)
}