package com.example.party_maker_android.data.clients

import com.example.party_maker_android.domain.models.Message
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface IMessageClient {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("Event/GetById")
    suspend fun getMessagesForCurrentUser(

    ): Response<List<Message>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("Message/GetAllForParty")
    suspend fun getMessagesForParty(
        @Header("Authorization") token: String,
        @Query("EventId") eventId: Int
    ): Response<List<Message>>
}