package com.example.party_maker_android.ui.fragments

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.Services.LocationService
import com.example.party_maker_android.models.EventEntity
import com.example.party_maker_android.network.model.MusicGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class AddEventViewModel : ViewModel() {

    var errorMessage = MutableLiveData<String>()
    var location = MutableLiveData<Location>()
    var isFormValid = MutableLiveData<Boolean>()
    private lateinit var eventRepo: EventRepository
    private lateinit var locationService: LocationService
    private var musicGenres = arrayOf<String>("House", "Rap", "Trap", "Rock", "Techno")


    fun setContext(context: Context){
        eventRepo = EventRepository(Dispatchers.IO, context)
        locationService = LocationService(context)
        location.value = locationService.getCurrentLocation()
    }

    private var isDescriptionValid: Boolean = false
        set(value){
            field = value
            checkFormState()
        }
    var descriptionValidationMessage = MutableLiveData<String?>()
    var description: String? = null
        set(value){
            if(value == null || value?.isEmpty()!!){
                descriptionValidationMessage.value = "Event description cannot be empty!"
                isDescriptionValid = false
            }
            else if(value.length <= 10){
                descriptionValidationMessage.value = "Event description must be longer than 10 characters!"
                isDescriptionValid = false
            }
            else{
                descriptionValidationMessage.value = null
                isDescriptionValid = true
                field = value
            }
        }

    private var isNameValid: Boolean = false
        set(value){
            field = value
            checkFormState()
        }
    var nameValidationMessage = MutableLiveData<String?>()
    var name: String? = null
        set(value){
            if(value == null || value?.isEmpty()!!){
                nameValidationMessage.value = "Name cannot be empty!"
                isNameValid = false
            }
            else if(value.length <= 5){
                nameValidationMessage.value = "Name must be longer than 5 characters!"
                isNameValid = false
            }
            else{
                nameValidationMessage.value = null
                isNameValid = true
                field = value
            }
        }

    private fun checkFormState(){
        isFormValid.value = isDescriptionValid && isNameValid
    }

    //TODO - to make valid event and add it
    private fun addEvent(){
        var eventToAdd: EventEntity = EventEntity().also{
            it.description = description
            if(location.value != null){
                it.longitude = location.value!!.longitude
                it.latitude = location.value!!.latitude
            }
            else{
                throw Error("Cannot determine location for new event!")
            }
        }

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