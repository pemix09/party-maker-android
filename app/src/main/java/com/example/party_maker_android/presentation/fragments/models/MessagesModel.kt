package com.example.party_maker_android.presentation.fragments.models

import android.content.Context
import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.domain.repositories.MessageRepository
import com.example.party_maker_android.domain.repositories.UserRepository
import com.example.party_maker_android.domain.services.UserService

class MessagesModel(context: Context) {
    private val TAG = "MessagesModel"
    private var messageRepository = MessageRepository(context)
    private var userRepository = UserRepository(context)

    suspend fun getMessagesForUserCurrent(): List<Message>?{
        var user = userRepository.getCurrentUser()!!
        if(user != null){
            return messageRepository.getMessagesForUser(user.id!!)
        }
        else{
            return null
        }
    }
}