package com.example.party_maker_android.presentation.fragments.viewModels

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.domain.repositories.UserRepository
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.presentation.fragments.models.ProfileModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"
    private lateinit var profileModel: ProfileModel
    var currentUser = MutableLiveData<UserEntity?>()
    var eventsToShow = MutableLiveData<List<EventEntity>?>()
    val errorMessage = MutableLiveData<String?>()
    var activeCard: Int? = null

    fun setContext(context: Context){
        profileModel = ProfileModel(context)
        viewModelScope.launch {
            currentUser.value = profileModel.getUser()
        }
    }

    fun fetchEventsToReview(){
        viewModelScope.launch {
            eventsToShow.value = profileModel.getEventsToReview()
        }
    }
    fun logout(){
        profileModel.logout()
    }

    fun setActiveCard(cardId: Int){
        activeCard = cardId

        viewModelScope.launch {
            when(cardId){
                R.id.your_events_card -> {
                    eventsToShow.value = profileModel.getOrganizedEvents()
                }
                R.id.events_to_review_card -> {
                    eventsToShow.value = profileModel.getEventsToReview()
                }
                R.id.participated_events_card -> {
                    eventsToShow.value = profileModel.getEventUserParticipate()
                }
                R.id.followed_events_card -> {
                    eventsToShow.value = profileModel.getFollowedEvents()
                }
            }
        }

    }

    fun clearEventsToShow(){
        eventsToShow.value = null
        activeCard = -200
    }

    fun changeProfilePicture(newPhoto: Bitmap){
        var photo = Base64Helper.getBase64StringFromBitmap(newPhoto)
        var updatedUser = currentUser.value.also {
            it?.photo = photo
        }
        currentUser.value = updatedUser
    }

    fun updateUser(){
        var user = currentUser.value
        if(user?.photo != null){
            viewModelScope.launch {
                profileModel.updateUser(user.photo!!)
            }
        }
    }
}