package com.example.party_maker_android.presentation.activities.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.presentation.activities.models.EventConversationModel
import kotlinx.coroutines.launch

class EventConversationViewModel(application: Application): AndroidViewModel(application) {
    private var eventConversationModel = EventConversationModel(application.applicationContext)
    val eventConversation = MutableLiveData<List<Message>>()
    val user = MutableLiveData<UserEntity>()

    fun refreshEventConversation(eventId: Int){
        viewModelScope.launch {
            user.value = eventConversationModel.getCurrentUser()
            eventConversationModel.eventId = eventId
            eventConversation.value = eventConversationModel.getMessagesForEvent()
        }
    }

    fun sendMessage(message: String){
        viewModelScope.launch {
            eventConversationModel.sendMessage(message)
            eventConversation.value = eventConversationModel.getMessagesForEvent()
        }
    }

}