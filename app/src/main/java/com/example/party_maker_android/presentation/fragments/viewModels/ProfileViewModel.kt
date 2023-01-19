package com.example.party_maker_android.presentation.fragments.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
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
    var followedEvents = MutableLiveData<List<EventEntity>?>()
    var organizedEvents = MutableLiveData<List<EventEntity>?>()
    val errorMessage = MutableLiveData<String?>()

    fun setContext(context: Context){
        profileModel = ProfileModel(context)
    }

    fun fetchData(){
        viewModelScope.launch {
            currentUser.value = profileModel.getUser()
            followedEvents.value = profileModel.getFollowedEvents()
            organizedEvents.value = profileModel.getOrganizedEvents()
        }
    }


}