package com.example.party_maker_android.Services

import android.content.Context
import com.example.party_maker_android.data.model.Token
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class UserService(private val context: Context) {
    private val accessTokenFile = "accessToken.txt"
    private val refreshTokenFile = "refreshToken.txt"

    fun SaveUserTokens(accessToken: String, refreshToken: String){

        var CryptoManager = CryptoManager()
        val accessTokenBytes = accessToken.encodeToByteArray()
        val accTokenFile = File(context.filesDir, this.accessTokenFile)
        if(accTokenFile.exists() == false){
            accTokenFile.createNewFile()
        }

        val fos = FileOutputStream(accTokenFile)
        CryptoManager.encrypt(bytes = accessTokenBytes, outputStream = fos)
        fos.close()

        val refreshTokenBytes= refreshToken.encodeToByteArray()
        val refTokenFile = File(context.filesDir, this.refreshTokenFile)
        if(refTokenFile.exists() == false){
            refTokenFile.createNewFile()
        }

        val fos2 = FileOutputStream(refTokenFile)
        CryptoManager.encrypt(bytes = refreshTokenBytes, outputStream = fos2)
        fos2.close()
    }

    fun getAccessToken(): String?{
        var CryptoManager = CryptoManager()
        val file = File(context.filesDir, this.accessTokenFile)
        val accessToken = CryptoManager.decrypt(inputStream = FileInputStream(file)).toString()

        return accessToken
    }

    fun GetRefreshToken(): String?{
        var CryptoManager = CryptoManager()
        val file = File(context.filesDir, this.refreshTokenFile)
        val refreshToken = CryptoManager.decrypt(inputStream = FileInputStream(file)).toString()

        return refreshToken
    }

}