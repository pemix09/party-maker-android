package com.example.party_maker_android.network

import android.content.Context
import com.example.party_maker_android.R
import com.example.party_maker_android.network.services.IUserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClientsFactory(val context: Context) {
    private val url: String = context.getString(R.string.BackEndAddress)

    //TODO logging body may slow down performance, shouldn't be used in production
    private var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private var httpClient = OkHttpClient.Builder()

    init {
        httpClient.addInterceptor(logging)
    }

    fun getUserClient(): IUserService{

        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(IUserService::class.java)
    }
}