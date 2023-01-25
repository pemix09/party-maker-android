package com.example.party_maker_android.data.clients

import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.data.requests.LoginRequest
import com.example.party_maker_android.data.requests.RefreshTokenRequest
import com.example.party_maker_android.data.requests.RegisterRequest
import com.example.party_maker_android.data.requests.UpdateUserRequest
import com.example.party_maker_android.data.responses.LoginResponse
import com.example.party_maker_android.data.responses.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.*

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

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/User/GetLogged")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): Response<UserEntity>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("User/GetByList")
    suspend fun getManyByIds(
        @Header("Authorization") token: String,
        @Query("UserIds") userIds: List<String>
    ): Response<List<UserEntity>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("User/UpdateUser")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body requestBody: UpdateUserRequest
    ): Response<Void>
}