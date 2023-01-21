package com.example.party_maker_android.presentation.activities.models

import android.content.Context
import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.domain.repositories.MessageRepository
import com.example.party_maker_android.domain.repositories.UserRepository

class EventConversationModel(private val context: Context) {
    private var messageRepository = MessageRepository(context)
    private var userRepository = UserRepository(context)

    suspend fun getMessagesForEvent(eventId: Int): List<Message>{
        return messageRepository.getMessagesForEvent(eventId)
    }

    suspend fun getCurrentUser(): UserEntity{
        return userRepository.getCurrentUser()!!
    }

}