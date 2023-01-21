package com.example.party_maker_android.presentation.fragments.models

import android.content.Context
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.domain.repositories.MessageRepository
import com.example.party_maker_android.domain.repositories.UserRepository
import com.example.party_maker_android.domain.services.UserService

class MessagesModel(context: Context) {
    private val TAG = "MessagesModel"
    private var messageRepository = MessageRepository(context)
    private var eventRepository = EventRepository(context)
    private var userRepository = UserRepository(context)

    suspend fun getEventsForCurrentUser(): List<EventEntity>?{
        var user = userRepository.getCurrentUser()!!
        return if(user != null){
            eventRepository.getFollowedEvents()
        } else{
            null
        }
    }
}