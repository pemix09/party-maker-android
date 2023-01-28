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
    var eventParticipants = MutableLiveData<List<UserEntity>?>()
    var currentUser = MutableLiveData<UserEntity?>()

    fun saveEventIdAndFetch(eventId: Int){
        viewModelScope.launch {
            currentUser.value = eventDetailsModel.getCurrentUser()
            event.value = eventDetailsModel.getEventDetails(eventId)
            if(event?.value?.participatorsIds != null && event?.value?.participatorsIds?.size != 0){
                eventParticipants.value = eventDetailsModel.getEventParticipants(event?.value?.participatorsIds!!)
            }
            else{
                eventParticipants.value = null
            }
        }
    }

    fun participateInEvent(){
        viewModelScope.launch {
            var user = eventDetailsModel.getCurrentUser()
            eventDetailsModel.participateInEvent(event?.value?.id!!)
            var newParticipators = event?.value?.participatorsIds?.toMutableList()
            newParticipators?.add(user?.id!!)
            eventParticipants.value = eventDetailsModel.getEventParticipants(newParticipators!!)
        }
    }
}