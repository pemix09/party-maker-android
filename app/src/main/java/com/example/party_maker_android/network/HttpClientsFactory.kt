package com.example.party_maker_android.network

import android.content.Context
import com.example.party_maker_android.R
import com.example.party_maker_android.network.services.IUserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClientsFactory(val context: Context) {
    private val url: String = context.getString(R.string.BackEndAddress)

    fun getUserClient(): IUserService{

        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IUserService::class.java)
    }
}