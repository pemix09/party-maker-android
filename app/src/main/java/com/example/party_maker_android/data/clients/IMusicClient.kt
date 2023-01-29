package com.example.party_maker_android.data.clients

import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.domain.models.MusicGenre
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface IMusicClient {

    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("Event/GetAllMusicGenres")
    suspend fun getMusicGenres(
        @Header("Authorization") token: String
    ): Response<List<MusicGenre>>
}