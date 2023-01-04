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
    private var errorMessage = MutableLiveData<String>()
    private var musicGenres: List<MusicGenre>? = null
    private var isFormValid: Boolean = false

    init {
        viewModelScope.launch {
            try{
                musicGenres = eventRepo.getMusicGenres()
            }
            catch(error: Exception) {
                errorMessage.value = error.message
            }
        }
    }

    private var isDescriptionValid: Boolean = false
        set(value){
            field = value
            checkFormState()
        }
    private var descriptionValidationMessage: String? = null
        set(value){
            if(value != null){
                isDescriptionValid = false
                field = value
            }
        }
    private var description: String? = null
        set(value){
            if(value == null || value?.isEmpty()!!){
                descriptionValidationMessage = "Event description cannot be empty!"
            }
            else if(value.length <= 10){
                descriptionValidationMessage = "Event description must be longer than 10 characters!"
            }
            else{
                descriptionValidationMessage = null
                isDescriptionValid = true
                field = value
            }
        }

    private fun checkFormState(){
        isFormValid = isDescriptionValid
    }

    //TODO - to make valid event and add it
    private fun addEvent(){
        var eventToAdd: EventEntity = EventEntity()

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