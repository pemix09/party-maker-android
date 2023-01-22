package com.example.party_maker_android.presentation.activities.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.presentation.activities.models.EventDetailsModel
import kotlinx.coroutines.launch

class EventDetailsViewModel(application: Application): AndroidViewModel(application) {
    private var eventDetailsModel = EventDetailsModel(application.applicationContext)
    var event = MutableLiveData<EventEntity>()
    var eventParticipants = MutableLiveData<List<UserEntity>>()

    fun saveEventIdAndFetch(eventId: Int){
        viewModelScope.launch {
            event.value = eventDetailsModel.getEventDetails(eventId)

        }
    }
}