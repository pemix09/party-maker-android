package com.example.party_maker_android.Services

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.example.party_maker_android.R
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec


class Encryption(val context: Context) {
    private val AndroidKeyStore = "AndroidKeyStore"
    private val AES_MODE = "AES/GCM/NoPadding"
    private val KEY_ALIAS = context.getString(R.string.KeyAlias)
    private val FIXED_IV = "dsadas"
    private lateinit var keyStore: KeyStore

//    fun generateKey(){
//        keyStore = KeyStore.getInstance(AndroidKeyStore)
//        keyStore.load(null)
//
//        if (!keyStore.containsAlias(KEY_ALIAS)) {
//            val keyGenerator: KeyGenerator =
//                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore)
//                keyGenerator.init(
//                    KeyGenParameterSpec.Builder(
//                        KEY_ALIAS,
//                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
//                )
//                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
//                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
//                    .setRandomizedEncryptionRequired(false)
//                    .build()
//            )
//            keyGenerator.generateKey()
//        }
//    }
//
//    fun getSecretKey(): Key? {
//        return keyStore.getKey(KEY_ALIAS, null)
//    }
//
//    fun encryptData(input: ByteArray): String {
//        val c: Cipher = Cipher.getInstance(AES_MODE)
//        c.init(
//            Cipher.ENCRYPT_MODE,
//            getSecretKey(),
//            GCMParameterSpec(128, FIXED_IV.toByteArray())
//        )
//        val encodedBytes: ByteArray = c.doFinal(input)
//        return Base64.encodeToString(encodedBytes, Base64.DEFAULT)
//    }
//
//    fun decryptData(encrypted: ByteArray): ByteArray{
//        val c = Cipher.getInstance(AES_MODE)
//        c.init(
//            Cipher.DECRYPT_MODE,
//            getSecretKey(),
//            GCMParameterSpec(128, FIXED_IV.toByteArray())
//        )
//        return c.doFinal(encrypted)
//    }


}