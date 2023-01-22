package com.example.party_maker_android.presentation.fragments.models

import android.content.Context
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.models.EventEntity

class EventListModel(context: Context) {
    private val eventsRepo = EventRepository(context)

    suspend fun getEventsToReview(): List<EventEntity>{
        return eventsRepo.getEventToReview()
    }
}