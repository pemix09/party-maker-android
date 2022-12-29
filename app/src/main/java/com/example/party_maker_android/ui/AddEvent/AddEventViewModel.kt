package com.example.party_maker_android.ui.AddEvent

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.models.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddEventViewModel(application: Application) : ViewModel() {

    private val eventRepo = EventRepository(Dispatchers.IO, application.applicationContext)

    //TODO - validation logic for event!
    private val event: EventEntity = EventEntity()
    private var errorMessage = MutableLiveData<String>()

    private fun addEvent(){

        viewModelScope.launch {
            try{
                eventRepo.createEvent(event)
            }
            catch(error: Exception){
                errorMessage.value = error.message
            }
        }
    }
}