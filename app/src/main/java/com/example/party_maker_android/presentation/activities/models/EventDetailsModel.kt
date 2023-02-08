package com.example.party_maker_android.presentation.activities.models

import android.content.Context
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.domain.repositories.UserRepository

class EventDetailsModel(context: Context) {
    private var eventsRepo = EventRepository(context)
    private var usersRepo = UserRepository(context)

    suspend fun getEventDetails(eventId: Int): EventEntity{
        return eventsRepo.getEventById(eventId)
    }

    suspend fun getEventParticipants(eventParticipants: List<String>): List<UserEntity>{
        val eventParticipants1 = usersRepo.getEventParticipants(eventParticipants)
        return eventParticipants1
    }

    suspend fun getCurrentUser(): UserEntity?{
        return usersRepo.getCurrentUser()
    }

    suspend fun participateInEvent(eventId: Int){
        eventsRepo.participateInEvent(eventId)
    }
    fun refresh(){
        eventsRepo.refresh()
    }
}