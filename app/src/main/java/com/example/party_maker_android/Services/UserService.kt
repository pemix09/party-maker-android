package com.example.party_maker_android.Services

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class UserService(private val context: Context) {
    private val accessTokenFile = "accessToken.txt"
    private val refreshTokenFile = "refreshToken.txt"

    fun SaveUserTokens(accessToken: String, refreshToken: String){
        this.setAccessToken(accessToken)
        this.setRefreshToken(refreshToken)
    }


    fun getAccessToken(): String?{
        var CryptoManager = CryptoManager(context)
        val file = File(context.filesDir, this.accessTokenFile)
        val accessToken = CryptoManager.decrypt(inputStream = FileInputStream(file)).decodeToString()

        return accessToken
    }

    fun GetRefreshToken(): String?{
        var CryptoManager = CryptoManager(context)
        val file = File(context.filesDir, this.refreshTokenFile)
        val refreshToken = CryptoManager.decrypt(inputStream = FileInputStream(file)).decodeToString()

        return refreshToken
    }

    private fun setAccessToken(accessToken: String){
        var cryptoManager = CryptoManager(context)
        val accessTokenBytes = accessToken.encodeToByteArray()
        val accTokenFile = File(context.filesDir, this.accessTokenFile)
        if(accTokenFile.exists() == false){
            accTokenFile.createNewFile()
        }

        val fos = FileOutputStream(accTokenFile)
        cryptoManager.encrypt(bytes = accessTokenBytes, outputStream = fos)
        fos.close()
    }

    private fun setRefreshToken(refreshToken: String){
        var cryptoManager = CryptoManager(context)
        val refreshTokenBytes= refreshToken.encodeToByteArray()
        val refTokenFile = File(context.filesDir, this.refreshTokenFile)
        if(refTokenFile.exists() == false){
            refTokenFile.createNewFile()
        }

        val fos = FileOutputStream(refTokenFile)
        cryptoManager.encrypt(bytes = refreshTokenBytes, outputStream = fos)
        fos.close()
    }
}