package com.example.party_maker_android.presentation.fragments.models

import android.content.Context
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.domain.repositories.UserRepository
import com.example.party_maker_android.domain.services.UserService

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

    suspend fun getEventsToReview(): List<EventEntity>{
        return eventRepository.getEventsToReview()
    }

    suspend fun getEventUserParticipate(): List<EventEntity>{
        return eventRepository.getParticipatesEvents()
    }

    fun logout(){
        userService.logOut()
    }
    suspend fun updateUser(userPhoto: String){
        var currentUser = userRepository.getCurrentUser()
        currentUser?.photo = userPhoto
        userRepository.updatePhoto(currentUser!!)
    }

}