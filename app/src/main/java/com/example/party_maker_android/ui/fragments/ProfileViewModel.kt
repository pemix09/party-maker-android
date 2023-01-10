package com.example.party_maker_android.ui.fragments

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.models.EventEntity
import com.example.party_maker_android.network.model.UserEntity
import com.example.party_maker_android.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private lateinit var userRepository: UserRepository
    private lateinit var eventRepository: EventRepository
    var currentUser = MutableLiveData<UserEntity?>()
    var eventsOfCurrentUser = MutableLiveData<List<EventEntity>?>()
    val errorMessage = MutableLiveData<String?>()

    fun setContext(context: Context){
        userRepository = UserRepository(Dispatchers.IO, context)
        eventRepository = EventRepository(Dispatchers.IO, context)
        fetchUser()
        fetchEvents()
    }

    private fun fetchUser(){
        var user: UserEntity? = null
        var errorMsg: String? = null

        viewModelScope.launch {
            try{
                user = userRepository.getCurrentUser()
            }
            catch(error: Exception){
                errorMsg = error.message
            }
            if(user == null){
                errorMsg = "Error fetching user data"
            }
        }

        if(errorMsg != null){
            errorMessage.value = errorMsg
        }

    }

    private fun fetchEvents(){
        var events: List<EventEntity>? = null
        var errorMsg: String? = null

        viewModelScope.launch {
            try{
                events = eventRepository.getEventsForCurrentUser()
            }
            catch(error: Exception){
                errorMsg = error.message
            }
            if(events == null){
                errorMsg = "Error fetching user data"
            }
        }

        if(errorMsg != null){
            errorMessage.value = errorMsg
        }
        if(events != null){
            eventsOfCurrentUser.value = events
        }
    }
}