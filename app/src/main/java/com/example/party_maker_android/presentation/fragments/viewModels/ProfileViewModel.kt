package com.example.party_maker_android.presentation.fragments.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.domain.repositories.UserRepository
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
        when(cardId){
            R.id.your_events_card -> {
                viewModelScope.launch {
                    eventsToShow.value = profileModel.getOrganizedEvents()
                }
            }
        }
    }

    fun clearEventsToShow(){
        eventsToShow.value = null
    }

}