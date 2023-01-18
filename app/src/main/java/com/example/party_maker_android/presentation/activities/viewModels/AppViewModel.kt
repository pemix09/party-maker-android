package com.example.party_maker_android.presentation.activities.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.domain.services.LocationService
import com.example.party_maker_android.domain.models.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.util.BoundingBox

class AppViewModel(private val applicationContext: Application): AndroidViewModel(applicationContext) {

    private var locationService = LocationService(applicationContext.applicationContext)
    private var eventRepo: EventRepository = EventRepository(Dispatchers.IO, applicationContext.applicationContext)
    private var events: List<EventEntity>? = null


    fun updateLocation(boundingBox: BoundingBox){
        var latitudeNorth: Double = boundingBox.latNorth
        var latitudeSouth: Double = boundingBox.latSouth
        var longitudeEast: Double = boundingBox.lonEast
        var longitudeWest: Double = boundingBox.lonWest
        events = loadEvents(latitudeNorth, latitudeSouth, longitudeEast, longitudeWest)
    }

    //first loading of events, should be based on localisation
    private fun loadEvents(latNorth: Double, latSouth: Double, lonEast: Double, lonWest: Double): List<EventEntity>?{
        var events: List<EventEntity>? = null

        viewModelScope.launch(Dispatchers.IO) {
            try {
                events = eventRepo.getEventsForArea(latNorth, latSouth, lonEast, lonWest)
            }
            catch(ex: Exception){
                Log.e("Fetching events error","Some error happend while fetching events: ${ex.message}")
            }
        }
        return events
    }



}