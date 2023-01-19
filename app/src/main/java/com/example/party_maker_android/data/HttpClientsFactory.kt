package com.example.party_maker_android.data

import android.content.Context
import com.example.party_maker_android.R
import com.example.party_maker_android.data.clients.IEventClient
import com.example.party_maker_android.data.clients.IMessageClient
import com.example.party_maker_android.data.clients.IUserClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class HttpClientsFactory(private val url: String) {

    //TODO logging body may slow down performance, shouldn't be used in production
    private var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private var httpClient = OkHttpClient.Builder()

    init {
        httpClient.addInterceptor(logging)
    }

    fun getUserClient(): IUserClient{
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(IUserClient::class.java)
    }

    fun getEventClient(): IEventClient{
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(IEventClient::class.java)
    }

    fun getMessageClient(): IMessageClient{
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(IMessageClient::class.java)
    }


}