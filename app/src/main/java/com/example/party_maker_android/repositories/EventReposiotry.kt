
package com.example.party_maker_android

import android.content.Context
import com.example.party_maker_android.Services.UserService
import com.example.party_maker_android.models.EventEntity
import com.example.party_maker_android.network.HttpClientsFactory
import com.example.party_maker_android.network.model.MusicGenre
import com.example.party_maker_android.network.clients.IEventClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

class EventRepository(private val dispatcher: CoroutineDispatcher, private val context: Context) {

    private val eventHttpClient: IEventClient = HttpClientsFactory(context).getEventClient()
    private val userService = UserService(context)

    suspend fun createEvent(eventToAdd: EventEntity){
        withContext(dispatcher){
            var accessToken = userService.getAccessToken()
            var formattedAccessToken = "Bearer ${accessToken?.token!!}"
            var response: Response<Void> = eventHttpClient.create(formattedAccessToken, eventToAdd)

            if(!response.isSuccessful) {

                if(response.code() == 500){
                    throw Exception("Server not available, check your internet connection and try later!")
                }
                else{
                    throw Exception("Adding event unsuccessful, cause: ${response.errorBody()?.toString()}")
                }
            }
        }
    }

    suspend fun getEventsForArea(latNorth: Double, latSouth: Double, lonEast: Double, lonWest: Double): List<EventEntity>?{
        var events: List<EventEntity>?

        withContext(dispatcher){
            var accessToken = userService.getAccessToken()
            var response: Response<List<EventEntity>> = eventHttpClient.getForArea(accessToken?.token!!, latNorth, latSouth, lonEast, lonWest)

            if(!response.isSuccessful){

                if(response.code() == 500){
                    throw Exception("Server not available, check your internet connection and try later!")
                }
                else{
                    throw Exception("Getting events by query unsuccessful, cause: ${response.errorBody()?.toString()}")
                }
            }

            events = response.body()
        }
        return events;
    }

    suspend fun getEventsForAreaWithQuery(query: String, latNorth: Double, latSouth: Double, lonEast: Double, lonWest: Double): List<EventEntity>?{
        var events: List<EventEntity>?

        withContext(dispatcher){
            var accessToken = userService.getAccessToken()
            var response: Response<List<EventEntity>> = eventHttpClient.getForAreaByQuery(accessToken?.token!!, query, latNorth, latSouth, lonEast, lonWest)

            if(!response.isSuccessful){

                if(response.code() == 500){
                    throw Exception("Server not available, check your internet connection and try later!")
                }
                else{
                    throw Exception("Getting events by query unsuccessful, cause: ${response.errorBody()?.toString()}")
                }
            }

            events = response.body()
        }
        return events;
    }

    suspend fun getMusicGenres(): List<MusicGenre>?{
        var genres: List<MusicGenre>?

        withContext(dispatcher) {
            var accessToken = userService.getAccessToken()
            var response: Response<List<MusicGenre>> =
                eventHttpClient.getMusicGenres(accessToken?.token!!)

            if (response.code() == 500) {
                throw Exception("Server not available, check your internet connection and try later!")
            } else {
                throw Exception(
                    "Getting events by query unsuccessful, cause: ${
                        response.errorBody()?.toString()
                    }"
                )
            }

            genres = response.body()
        }
        return genres
    }
}