package com.example.party_maker_android.network.services

import com.example.party_maker_android.models.EventEntity
import com.example.party_maker_android.network.model.MusicGenre
import retrofit2.Response
import retrofit2.http.*

interface IEventService {

    @GET("Event/GetById")
    suspend fun getById(
        @Header("Authorization") token: String,
        @Query("EventId") eventId: Int
    ): Response<EventEntity>

    @GET("Event/GetByQuery")
    suspend fun GetByQuery(
        @Header("Authorization") token: String,
        @Query("Query") query: String
    ): Response<List<EventEntity>>

    @POST("Event/Create")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body event: EventEntity
    ): Response<Void>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("Event/GetMusicGenres")
    suspend fun getMusicGenres(
        @Header("Authorization") token: String
    ): Response<List<MusicGenre>>
}