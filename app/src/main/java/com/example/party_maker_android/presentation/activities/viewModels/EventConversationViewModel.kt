package com.example.party_maker_android.presentation.activities.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.presentation.activities.models.EventConversationModel
import kotlinx.coroutines.launch

class EventConversationViewModel(application: Application): AndroidViewModel(application) {
    private var eventConversationModel = EventConversationModel(application.applicationContext)
    val eventConversation = MutableLiveData<List<Message>>()

    fun refreshEventConversation(eventId: Int){
        viewModelScope.launch {
            eventConversation.value = eventConversationModel.getMessagesForEvent(eventId)
        }
    }
}