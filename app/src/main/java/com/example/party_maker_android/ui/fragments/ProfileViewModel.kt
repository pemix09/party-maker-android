package com.example.party_maker_android.ui.fragments

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.models.EventEntity
import com.example.party_maker_android.network.model.UserEntity
import com.example.party_maker_android.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"
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
        viewModelScope.launch {
            try{
                currentUser.value = userRepository.getCurrentUser()
            }
            catch(error: Exception){
                Log.e(TAG, "Error fetching user details: ${error.message}")
                errorMessage.value = error.message
            }
        }
    }

    private fun fetchEvents(){
        viewModelScope.launch {
            try{
                eventsOfCurrentUser.value = eventRepository.getEventsForCurrentUser()
            }
            catch(error: Exception){
                Log.e(TAG, "Some error while fetching events: ${error.message}")
                errorMessage.value = error.message
            }
        }
    }
}