package com.example.party_maker_android.presentation.activities.models

import android.content.Context
import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.domain.repositories.MessageRepository

class EventConversationModel(private val context: Context) {
    private var messageRepository = MessageRepository(context)

    suspend fun getMessagesForEvent(eventId: Int): List<Message>{
        return messageRepository.getMessagesForEvent(eventId)
    }
}