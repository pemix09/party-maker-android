package com.example.party_maker_android.presentation.fragments.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.presentation.fragments.models.ProfileModel
import com.example.party_maker_android.presentation.fragments.models.SearchEventsModel
import kotlinx.coroutines.launch

class SearchEventsViewModel : ViewModel() {
    private lateinit var searchEventsModel: SearchEventsModel
    val searchResult = MutableLiveData<List<EventEntity>>()
    val searchMessage = MutableLiveData<String>()

    fun setContext(context: Context){
        searchEventsModel = SearchEventsModel(context)
    }
    fun search(search: String){
        viewModelScope.launch {
            try{
                searchResult.value = searchEventsModel.searchEvents(search)
            }
            catch(error: Error){
                searchMessage.value = error.message
            }
        }
    }
}