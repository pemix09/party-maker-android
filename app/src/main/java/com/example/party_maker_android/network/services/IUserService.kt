package com.example.party_maker_android.network.services

import com.example.party_maker_android.network.Requests.LoginRequest
import com.example.party_maker_android.network.Requests.RegisterRequest
import com.example.party_maker_android.network.responses.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface IUserService {

    @Headers("Content-Type: application/json", "Accept: text/plain")
    @POST("/User/Login")
    suspend fun Login(
        @Body requestBody: LoginRequest,
    ): Response<LoginResponse>

    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("/User/Register")
    suspend fun Register(
        @Body requestBody: RegisterRequest
    ): Response<Void>
}