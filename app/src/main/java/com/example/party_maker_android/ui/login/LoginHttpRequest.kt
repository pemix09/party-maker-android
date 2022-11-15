package com.example.party_maker_android.ui.login

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.party_maker_android.R
import com.github.kittinunf.fuel.Fuel
import java.net.URI
import java.net.URLEncoder
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result;
import com.google.gson.Gson


class LoginHttpRequest(private val loginContext: Context) {


    fun invoke(email: String, password: String){
        val url = loginContext.getString(R.string.LoginEndpointAddress)
        val body = LoginRequestBody(email, password)
        val jsonBody = Gson().toJson(body)
        val (request, response, result) = Fuel.post(url)
            .body(jsonBody)
            .response()

        if(result is Result.Success){
            val data = result.get()
            println(data)
        }
        else if(result is Result.Failure){
            val ex = result.getException()
            println(ex)
        }
    }
}