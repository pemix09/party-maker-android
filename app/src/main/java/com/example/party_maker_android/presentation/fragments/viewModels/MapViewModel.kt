package com.example.party_maker_android.presentation.fragments.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.models.EventEntity
import kotlinx.coroutines.launch
import org.osmdroid.util.BoundingBox

class MapViewModel : ViewModel() {
    val eventsToShow = MutableLiveData<List<EventEntity>>()
    lateinit var eventRepository: EventRepository

    fun setContext(context: Context){
        eventRepository = EventRepository(context)
    }
    fun getEventsForBoundingBox(boundingBox: BoundingBox){
        viewModelScope.launch {
            eventsToShow.value = eventRepository.getEventsForArea(boundingBox.latNorth,
                boundingBox.latSouth,
                boundingBox.lonEast,
                boundingBox.lonWest)
        }
    }
}