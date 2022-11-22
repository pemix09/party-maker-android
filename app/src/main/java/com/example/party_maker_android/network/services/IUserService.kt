package com.example.party_maker_android.network.services

import com.example.party_maker_android.network.Requests.LoginRequest
import com.example.party_maker_android.network.Requests.RegisterRequest
import com.example.party_maker_android.network.responses.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface IUserService {

    @POST("User/Login")
    suspend fun Login(
        @Body requestBody: LoginRequest,
    ): Response<LoginResponse>

    @POST("User/Register")
    suspend fun Register(
        @Body requestBody: RegisterRequest
    ): Response<Void>
}