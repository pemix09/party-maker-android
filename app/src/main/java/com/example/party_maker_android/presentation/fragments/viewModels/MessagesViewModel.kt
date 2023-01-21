package com.example.party_maker_android.presentation.fragments.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.presentation.fragments.models.MessagesModel
import com.example.party_maker_android.presentation.fragments.models.ProfileModel
import kotlinx.coroutines.launch

class MessagesViewModel: ViewModel() {
    private lateinit var messagesModel: MessagesModel
    var messagesToShow = MutableLiveData<List<Message>>()

    fun setContext(context: Context){
        messagesModel = MessagesModel(context)
    }
    fun refreshData(){
        viewModelScope.launch {
            messagesToShow.value = messagesModel.getMessagesForUserCurrent()

        }
    }
}