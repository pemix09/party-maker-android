package com.example.party_maker_android.Services

import android.content.Context
import com.example.party_maker_android.network.model.AccessToken
import com.example.party_maker_android.network.model.RefreshToken
import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class UserService(private val context: Context) {
    private val accessTokenFile = "accessToken.txt"
    private val refreshTokenFile = "refreshToken.txt"
    val gson = Gson()

    fun saveUserTokens(accessToken: AccessToken, refreshToken: RefreshToken){
        this.setAccessToken(accessToken)
        this.setRefreshToken(refreshToken)
    }


    fun getAccessToken(): AccessToken? {
        var cryptoManager = CryptoManager(context)
        val file = File(context.filesDir, this.accessTokenFile)
        val accessTokenJson =
            cryptoManager.decrypt(inputStream = FileInputStream(file)).decodeToString()

        return gson.fromJson(accessTokenJson, AccessToken::class.java)
    }

    fun GetRefreshToken(): RefreshToken? {
        var cryptoManager = CryptoManager(context)
        val file = File(context.filesDir, this.refreshTokenFile)
        val refreshTokenJson =
            cryptoManager.decrypt(inputStream = FileInputStream(file)).decodeToString()

        return gson.fromJson(refreshTokenJson, RefreshToken::class.java)
    }

    private fun setAccessToken(accessToken: AccessToken){
        var cryptoManager = CryptoManager(context)
        val accessTokenJson: String = gson.toJson(accessToken)
        val accessTokenBytes = accessTokenJson.encodeToByteArray()
        val accTokenFile = File(context.filesDir, this.accessTokenFile)

        if(accTokenFile.exists() == false){
            accTokenFile.createNewFile()
        }

        val fos = FileOutputStream(accTokenFile)
        cryptoManager.encrypt(bytes = accessTokenBytes, outputStream = fos)
        fos.close()
    }

    private fun setRefreshToken(refreshToken: RefreshToken){
        var cryptoManager = CryptoManager(context)
        var refreshTokenJson = gson.toJson(refreshToken)
        val refreshTokenBytes= refreshTokenJson.encodeToByteArray()
        val refTokenFile = File(context.filesDir, this.refreshTokenFile)

        if(refTokenFile.exists() == false){
            refTokenFile.createNewFile()
        }

        val fos = FileOutputStream(refTokenFile)
        cryptoManager.encrypt(bytes = refreshTokenBytes, outputStream = fos)
        fos.close()
    }
}