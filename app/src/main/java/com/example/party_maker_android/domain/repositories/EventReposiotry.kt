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

class EventRepository(private val context: Context) {

    private val backEndAddress = context.getString(R.string.BackEndAddress)
    private val eventHttpClient: IEventClient = HttpClientsFactory(backEndAddress).getEventClient()
    private val userService = UserService(context)
    private val TAG = "EventRepository"

    //in memory objects - the fastest access!
    companion object {
        private var followed: List<EventEntity>? = null
        private var organized: List<EventEntity>? = null
        private var participates: List<EventEntity>? = null
    }

    suspend fun createEvent(eventToAdd: EventEntity) {
        withContext(Dispatchers.IO) {
            var accessToken = userService.getAccessToken()
            var formattedAccessToken = "Bearer ${accessToken?.token!!}"
            var response: Response<Void> = eventHttpClient.create(formattedAccessToken, eventToAdd)

            if (!response.isSuccessful) {

                if (response.code() == 500) {
                    throw Exception("Server not available, check your internet connection and try later!")
                } else {
                    throw Exception(
                        "Adding event unsuccessful, cause: ${
                            response.errorBody()?.toString()
                        }"
                    )
                }
            }
        }
    }

    suspend fun getFollowedEvents(): List<EventEntity> {
        //if events are in memory
        if (followed != null) return followed!!
        //if we have to fetch events
        else {
            getEventsForCurrentUser()
        }
        //after previous steps, followed is never going to be null
        return followed!!
    }

    suspend fun getOrganizedEvents(): List<EventEntity> {
        //if events are in memory
        if (organized != null) return organized!!
        //if we have to fetch events
        else {
            this.getEventsForCurrentUser()
        }
        //after previous steps, organized is never going to be null
        return organized!!
    }

    suspend fun getParticipatesEvents(): List<EventEntity>{
        if(participates != null) return participates!!
        else{
            this.getEventsForCurrentUser()
        }
        return participates!!
    }

    //TODO - make right things, not use with context!
    suspend fun getEventsForArea(
        latNorth: Double,
        latSouth: Double,
        lonEast: Double,
        lonWest: Double
    ): List<EventEntity>? {
        var events: List<EventEntity>?

        withContext(Dispatchers.IO) {
            var accessToken = userService.getAccessToken()
            var response: Response<List<EventEntity>> = eventHttpClient.getForArea(
                accessToken?.token!!,
                latNorth,
                latSouth,
                lonEast,
                lonWest
            )

            if (!response.isSuccessful) {

                if (response.code() == 500) {
                    throw Exception("Server not available, check your internet connection and try later!")
                } else {
                    throw Exception(
                        "Getting events by query unsuccessful, cause: ${
                            response.errorBody()?.toString()
                        }"
                    )
                }
            }

            events = response.body()
        }
        return events;
    }

    //TODO - make right things, not use with context!
    suspend fun getEventsForAreaWithQuery(
        query: String,
        latNorth: Double,
        latSouth: Double,
        lonEast: Double,
        lonWest: Double
    ): List<EventEntity>? {
        var events: List<EventEntity>?

        withContext(Dispatchers.IO) {
            var accessToken = userService.getAccessToken()
            var response: Response<List<EventEntity>> = eventHttpClient.getForAreaByQuery(
                accessToken?.token!!,
                query,
                latNorth,
                latSouth,
                lonEast,
                lonWest
            )

            if (!response.isSuccessful) {

                if (response.code() == 500) {
                    throw Exception("Server not available, check your internet connection and try later!")
                } else {
                    throw Exception(
                        "Getting events by query unsuccessful, cause: ${
                            response.errorBody()?.toString()
                        }"
                    )
                }
            }

            events = response.body()
        }
        return events;
    }

    suspend fun getMusicGenres(): List<MusicGenre>? {
        var genres: List<MusicGenre>?

        withContext(Dispatchers.IO) {
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

    private suspend fun getEventsForCurrentUser(){
        var accessToken = userService.getAccessToken()
        var formattedAccessToken = "Bearer ${accessToken?.token!!}"
        var response: Response<GetAllEvensForUserResponse> =
            eventHttpClient.getAllOfCurrentUser(formattedAccessToken)

        if (!response.isSuccessful) {
            if (response.code() == 500) {
                throw Exception("Server not available, check your internet connection and try later!")
            } else {
                throw Exception(
                    "Getting events for user unsuccessful, cause: ${
                        response.errorBody()?.toString()
                    }"
                )
            }
        }
        else{
            organized = response.body()?.organized
            followed = response.body()?.followed
            participates = response.body()?.participates
        }
    }
}