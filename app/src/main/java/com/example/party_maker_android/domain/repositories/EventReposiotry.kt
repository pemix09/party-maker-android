
package com.example.party_maker_android

import android.content.Context
import android.util.Log
import com.example.party_maker_android.domain.services.UserService
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.domain.models.MusicGenre
import com.example.party_maker_android.data.clients.IEventClient
import com.example.party_maker_android.data.responses.GetAllEvensForUserResponse
import kotlinx.coroutines.*
import retrofit2.Response

class EventRepository(private val dispatcher: CoroutineDispatcher, private val context: Context) {

    private val eventHttpClient: IEventClient = HttpClientsFactory(context).getEventClient()
    private val userService = UserService(context)
    private val TAG = "EventRepository"

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

    suspend fun getEventsForCurrentUser(): GetAllEvensForUserResponse?{
        var getEventsJob = GlobalScope.async(dispatcher){
            var accessToken = userService.getAccessToken()
            var formattedAccessToken = "Bearer ${accessToken?.token!!}"
            var response: Response<GetAllEvensForUserResponse> = eventHttpClient.getAllOfCurrentUser(formattedAccessToken)

            if(!response.isSuccessful){
                if(response.code() == 500){
                    throw Exception("Server not available, check your internet connection and try later!")
                }
                else{
                    throw Exception("Getting events for user unsuccessful, cause: ${response.errorBody()?.toString()}")
                }
            }
            Log.i(TAG, "Getting user's events successful!")
            return@async response.body()
        }
        Log.i(TAG, "Events returned to main thread")
        return getEventsJob.await();
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