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
    private val gson = Gson()



    fun saveUserTokens(accessToken: AccessToken, refreshToken: RefreshToken){
        this.setAccessToken(accessToken)
        this.setRefreshToken(refreshToken)
    }

    companion object{
        private var accessToken: AccessToken? = null
        private var refreshToken: RefreshToken? = null
    }


    fun getAccessToken(): AccessToken?{
        if(accessToken != null){
            return accessToken
        }
        else{
            accessToken = readAccessToken()
            return accessToken
        }
    }

    fun getRefreshToken(): RefreshToken?{
        if(refreshToken != null){
            return refreshToken
        }
        else{
            refreshToken = readRefreshToken()
            return refreshToken
        }
    }

    fun SaveRefreshedAccessToken(accessToken: AccessToken){
        setAccessToken(accessToken)
    }

    private fun readAccessToken(): AccessToken? {
        var cryptoManager = CryptoManager(context)
        val file = File(context.filesDir, this.accessTokenFile)

        if(file.exists()){
            val accessTokenJson =
                cryptoManager.decrypt(inputStream = FileInputStream(file)).decodeToString()

            return gson.fromJson(accessTokenJson, AccessToken::class.java)
        }
        else{
            return null
        }

    }

    private fun readRefreshToken(): RefreshToken? {
        var cryptoManager = CryptoManager(context)
        val file = File(context.filesDir, this.refreshTokenFile)

        if(file.exists()){
            val refreshTokenJson =
                cryptoManager.decrypt(inputStream = FileInputStream(file)).decodeToString()

            return gson.fromJson(refreshTokenJson, RefreshToken::class.java)
        }
        else{
            return null
        }
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