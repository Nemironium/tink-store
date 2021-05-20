package io.nemiron.storetk.encryptor

import android.app.Application
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AesGcmKeyManager
import com.google.crypto.tink.integration.android.AndroidKeysetManager

private const val KEYSET_NAME = "master_keyset"
private const val PREFERENCE_FILE = "master_key_preference"
private const val MASTER_KEY_URI = "android-keystore://master_key"

internal fun provideAead(application: Application): Aead {
    AeadConfig.register()

    return AndroidKeysetManager.Builder()
        .withSharedPref(application, KEYSET_NAME, PREFERENCE_FILE)
        .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
        .withMasterKeyUri(MASTER_KEY_URI)
        .build()
        .keysetHandle
        .getPrimitive(Aead::class.java)
}