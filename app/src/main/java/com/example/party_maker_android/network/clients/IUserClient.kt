package com.example.party_maker_android.network.clients

import com.example.party_maker_android.network.requests.LoginRequest
import com.example.party_maker_android.network.requests.RefreshTokenRequest
import com.example.party_maker_android.network.requests.RegisterRequest
import com.example.party_maker_android.network.responses.LoginResponse
import com.example.party_maker_android.network.responses.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IUserClient {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/User/Login")
    suspend fun Login(
        @Body requestBody: LoginRequest,
    ): Response<LoginResponse>

    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("/User/Register")
    suspend fun Register(
        @Body requestBody: RegisterRequest
    ): Response<Void>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/User/RefreshToken")
    suspend fun RefreshToken(
        @Body requestBody: RefreshTokenRequest
    ): Response<RefreshTokenResponse>


}