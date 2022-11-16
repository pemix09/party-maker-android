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
                       private val password: String) : AsyncTask<Void, Void, String>() {


//    fun invoke(email: String, password: String){
//        val url = loginContext.getString(R.string.LoginEndpointAddress)
//        val body = LoginRequestBody(email, password)
//        val jsonBody = Gson().toJson(body)
//        val (request, response, result) = Fuel.post(url)
//            .body(jsonBody)
//            .response()
//
//        if(result is Result.Success){
//            val data = result.get()
//            println(data)
//        }
//        else if(result is Result.Failure){
//            val ex = result.getException()
//            println(ex)
//        }
//    }

    override fun doInBackground(vararg p0: Void?): String? {
        val url = loginContext.getString(R.string.LoginEndpointAddress)
        val body = LoginRequestBody(this.email, this.password)
        val jsonBody = Gson().toJson(body)
        val (request, response, result) = Fuel.post(url)
            .header("Content-Type" to "application/json")
            .body(jsonBody)
            .response()

        if(result is Result.Success){
            val data = result.get()
            println(data)
            return data.toString()
        }
        else if(result is Result.Failure){
            val ex = result.getException()
            println(ex)
        }

        return null
    }
}