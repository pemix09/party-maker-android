package com.example.party_maker_android.presentation.activities.models

import android.content.Context
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.models.EventEntity

class EventDetailsModel(context: Context) {
    private var eventsRepo = EventRepository(context)
    private var eventId: Int? = null

    suspend fun getEventDetails(eventId: Int): EventEntity{
        this.eventId = eventId
        return eventsRepo.getEventById(eventId)
    }
}