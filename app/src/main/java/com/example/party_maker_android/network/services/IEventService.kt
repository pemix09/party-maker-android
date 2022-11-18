package com.example.party_maker_android.network.services

import com.example.party_maker_android.network.model.EventEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IEventService {

    @GET("GetById")
    suspend fun GetById(
        @Header("Authorization") token: String,
        @Query("EventId") eventId: Int
    ): EventEntity
}