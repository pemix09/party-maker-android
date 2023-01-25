package com.example.party_maker_android.data.clients

import com.example.party_maker_android.data.requests.ParticipateInEventRequest
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.models.MusicGenre
import com.example.party_maker_android.data.responses.GetAllEvensForUserResponse
import retrofit2.Response
import retrofit2.http.*

interface IEventClient {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("Event/GetById")
    suspend fun getById(
        @Header("Authorization") token: String,
        @Query("EventId") eventId: Int
    ): Response<EventEntity>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("Event/GetForAreaByQuery")
    suspend fun getForAreaByQuery(
        @Header("Authorization") token: String,
        @Query("Query") query: String,
        @Query("latNorth") latNorth: Double,
        @Query("latSouth") latSouth: Double,
        @Query("lonEast") lonEast: Double,
        @Query("lonWest") lonWest: Double
    ): Response<List<EventEntity>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("Event/GetByQuery")
    suspend fun getByQuery(
        @Header("Authorization") token: String,
        @Query("Name") query: String
    ): Response<List<EventEntity>>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("Event/GetForArea")
    suspend fun getForArea(
        @Header("Authorization") token: String,
        @Query("latNorth") latNorth: Double,
        @Query("latSouth") latSouth: Double,
        @Query("lonEast") lonEast: Double,
        @Query("lonWest") lonWest: Double
    ): Response<List<EventEntity>>

    @Headers("Content-Type: application/json", "Accept: application/json")
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

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("Event/GetAllOfCurrentUser")
    suspend fun getAllOfCurrentUser(
        @Header("Authorization") token: String
    ): Response<GetAllEvensForUserResponse>

    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("Event/TakePartInEvent")
    suspend fun participateInEvent(
        @Header("Authorization") token: String,
        @Body requestBody: ParticipateInEventRequest
    ): Response<Void>

}