package com.example.party_maker_android.domain.repositories

import android.content.Context
import com.example.party_maker_android.R
import com.example.party_maker_android.data.HttpClientsFactory
import com.example.party_maker_android.data.requests.CreateMessageRequest
import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.domain.services.UserService

class MessageRepository(private val context: Context) {
    private val backEndAddress = context.getString(R.string.BackEndAddress)
    private val messageHttpClient = HttpClientsFactory(backEndAddress).getMessageClient()
    private val userService = UserService(context)

    companion object{

    }

    suspend fun getMessagesForEvent(eventId: Int): List<Message>{
        var accessToken = userService.getAccessToken()
        var formattedAccessToken = "Bearer ${accessToken?.token!!}"

        var response = messageHttpClient.getMessagesForParty(formattedAccessToken, eventId)

        if(response.isSuccessful){
            return response.body()!!
        }
        else{
            throw Error(response.errorBody().toString())
        }
    }

    suspend fun sendMessageToEvent(msg: String, eventId: Int){
        var accessToken = userService.getAccessToken()
        var formattedAccessToken = "Bearer ${accessToken?.token!!}"
        var request = CreateMessageRequest(msg, eventId)
        var response = messageHttpClient.sendMessage(formattedAccessToken, request)

        if(!response.isSuccessful){
            throw Error(response.errorBody().toString())
        }
    }
    fun getMessagesForUser(userId: String): List<Message>{

        //var messagesResponse = messageHttpClient.
        throw NotImplementedError()
        return null!!
        //TODO
    }
}