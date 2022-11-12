package com.example.party_maker_android.Services

import android.content.Context
import com.example.party_maker_android.data.model.Token
import java.net.URL

class HttpService(val context: Context) {

    fun LoginUser(email: String, password: String): Token {

        val url: String = ConfigService.GetBackEndAddress(context)

        val tokenString: String = URL(url).openStream().use {
            it.bufferedReader().readLine()
        }

        return Token(tokenString)
    }
}