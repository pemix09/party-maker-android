package com.example.party_maker_android.ui.fragments

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.models.EventEntity
import com.example.party_maker_android.network.model.MusicGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class AddEventViewModel(application: Application) : ViewModel() {

    private val eventRepo = EventRepository(Dispatchers.IO, application.applicationContext)

    //TODO - validation logic for event!
    private val eventToAdd: EventEntity = EventEntity()
    private var errorMessage = MutableLiveData<String>()
    private var musicGenres: List<MusicGenre>? = null

    fun initialize(){
        viewModelScope.launch {
            try{
                musicGenres = eventRepo.getMusicGenres()
            }
            catch(error: Exception) {
                errorMessage.value = error.message
            }
        }
    }

    private fun addEvent(){

        viewModelScope.launch {
            try{
                eventRepo.createEvent(eventToAdd)
            }
            catch(error: Exception){
                errorMessage.value = error.message
            }
        }
    }
}