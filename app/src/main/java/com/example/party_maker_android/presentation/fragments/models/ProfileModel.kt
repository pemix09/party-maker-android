package com.example.party_maker_android.presentation.fragments.models

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.domain.repositories.UserRepository
import com.example.party_maker_android.domain.services.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileModel(context: Context) {
    private val TAG = "Profile model"
    private var userRepository = UserRepository(context)
    private var eventRepository = EventRepository(context)
    private var userService = UserService(context)

    suspend fun getUser(): UserEntity {
        return userRepository.getCurrentUser()!!
    }

    suspend fun getOrganizedEvents(): List<EventEntity>{
        return eventRepository.getOrganizedEvents()
    }
    suspend fun getFollowedEvents(): List<EventEntity>{
        return eventRepository.getFollowedEvents()
    }

    fun logout(){
        userService.logOut()
    }
}