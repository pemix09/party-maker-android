package com.example.party_maker_android.network.services

import com.example.party_maker_android.models.EventEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface IEventService {

    @GET("GetById")
    suspend fun getById(
        @Header("Authorization") token: String,
        @Query("EventId") eventId: Int
    ): Response<EventEntity>

    @GET("GetByQuery")
    suspend fun GetByQuery(
        @Header("Authorization") token: String,
        @Query("Query") query: String
    ): Response<List<EventEntity>>

    @POST("Create")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body event: EventEntity
    ): Response<Void>
}