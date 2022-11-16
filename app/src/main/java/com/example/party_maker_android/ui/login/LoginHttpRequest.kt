package com.example.party_maker_android.ui.login

import android.content.Context
import android.os.AsyncTask
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


class LoginHttpRequest(private val loginContext: Context,
                       private val email: String,
                       private val password: String) : AsyncTask<Void, Void, LoginHttpResponse>() {

    override fun doInBackground(vararg p0: Void?): LoginHttpResponse? {
        val url = loginContext.getString(R.string.LoginEndpointAddress)
        val body = LoginRequestBody(this.email, this.password)
        val jsonBody = Gson().toJson(body)
        val (request, response, result) = Fuel.post(url)
            .header("Content-Type" to "application/json")
            .header("Accept" to "application/json")
            .body(jsonBody)
            .response()

        if(result is Result.Success){

            try{
                val accessToken = result.get().toString()
                val refreshToken = response.headers["RefreshToken"].first()

                return LoginHttpResponse(accessToken, refreshToken)
            } catch(e: Exception){
                return null
            }

        }
        else if(result is Result.Failure){
            val ex = result.getException()
        }

        return null
    }
}