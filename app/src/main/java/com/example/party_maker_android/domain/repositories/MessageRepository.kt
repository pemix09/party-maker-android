package com.example.party_maker_android.domain.repositories

import android.content.Context
import com.example.party_maker_android.R
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.domain.models.Message

class MessageRepository(private val context: Context) {
    private val backEndAddress = context.getString(R.string.BackEndAddress)
    private val messageHttpClient = HttpClientsFactory(backEndAddress).getMessageClient()

    companion object{

    }

    fun getMessagesForEvent(eventId: Int): List<Message>{
        return null!!
        //TODO kurde faja
    }

    fun getMessagesForUser(userId: String): List<Message>{

        //var messagesResponse = messageHttpClient.
        return null!!
        //TODO
    }
}