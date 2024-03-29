package com.example.party_maker_android.presentation.fragments.viewModels

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.services.LocationService
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.presentation.fragments.models.AddEventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddEventViewModel : ViewModel() {
    val TAG = "AddEventViewModel"
    var message = MutableLiveData<String>()
    var eventAddedSuccessfully = MutableLiveData<Boolean>()
    var location = MutableLiveData<Location?>()
    var photo: String? = null
        set(value){
            field = value
            checkFormState()
        }
    var date: Date? = null
        get(){
            return field ?:
            Calendar.getInstance().also{
                it.time = Calendar.getInstance().time
                it.add(Calendar.MONTH, 1)
            }.time
        }
        set(value){
            field = value
            checkFormState()
        }
    var place: String? = null
        set(value){
            field = value
            checkFormState()
        }
    var isFormValid = MutableLiveData<Boolean>()
    private lateinit var eventRepo: EventRepository
    private lateinit var addEventModel: AddEventModel

    override fun onCleared() {
        super.onCleared()
        //addEventModel.location.removeObserver {  }
    }

    fun setContext(context: Context){
        eventRepo = EventRepository(context)
        addEventModel = AddEventModel(context)

        addEventModel.location.observeForever {
            location.value = it
        }
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

    fun addEventPhoto(photo: Bitmap){
        this.photo = Base64Helper.getBase64StringFromBitmap(photo)
    }
    private fun checkFormState(){
        //check for location on form validation
        if(addEventModel.location.value != null && addEventModel.location.value != location.value){
            location.value = addEventModel.location.value
        }
        isFormValid.value = isDescriptionValid && isNameValid && photo != null && location.value != null && place != null
    }


    fun addEvent(){
        var eventToAdd: EventEntity = EventEntity().also{
            it.name = name
            it.description = description
            it.longitude = location.value!!.longitude
            it.latitude = location.value!!.latitude
            it.photo = photo
            it.musicGenreId = 1
            it.place = place
            it.type = "vixa"
            it.date = date
        }

        viewModelScope.launch {
            try{
                eventRepo.createEvent(eventToAdd)
                eventAddedSuccessfully.value = true
                message.value = "Event added successfully!"
                eventRepo.refresh()
            }
            catch(error: Exception){
                message.value = error.message
            }
        }
    }
}