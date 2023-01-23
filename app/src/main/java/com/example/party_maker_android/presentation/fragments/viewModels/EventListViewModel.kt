package com.example.party_maker_android.presentation.fragments.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.presentation.fragments.models.EventListModel
import kotlinx.coroutines.launch

class EventListViewModel : ViewModel() {
    private val TAG = "EventListViewModel"
    val eventsToShow = MutableLiveData<List<EventEntity>>()
    lateinit var eventsListModel: EventListModel

    fun setContext(context: Context){
        eventsListModel = EventListModel(context)
    }

    fun getEvents(typeOfEvents: Int){
        Log.i(TAG, "type of events: $typeOfEvents")
        viewModelScope.launch {
            when(typeOfEvents){
                R.id.events_to_review_card -> {
                    eventsToShow.value = eventsListModel.getEventsToReview()
                }
            }
        }
    }
}