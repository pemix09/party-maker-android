package com.example.party_maker_android.presentation.fragments.models

import android.content.Context
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.models.EventEntity

class SearchEventsModel(context: Context) {
    private val eventsRepository = EventRepository(context)

    suspend fun searchEvents(searchQuery: String): List<EventEntity>?{
        return eventsRepository.getEventsByQuery(searchQuery)
    }
}