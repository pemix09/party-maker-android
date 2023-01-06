package com.example.party_maker_android.ui.map

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.party_maker_android.EventRepository
import com.example.party_maker_android.Services.LocationService
import com.example.party_maker_android.models.EventEntity
import com.example.party_maker_android.network.requests.RegisterRequest
import com.example.party_maker_android.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MapModel(private val applicationContext: Application): AndroidViewModel(applicationContext) {

    private var locationService = LocationService(applicationContext.applicationContext)
    private var eventRepo: EventRepository = EventRepository(Dispatchers.IO, applicationContext.applicationContext)
    private var events: List<EventEntity>? = null

    init {
        updateLocation()
    }


    fun updateLocation(){
        events = loadEvents(locationService.getCurrentLocation())
    }

    //first loading of events, should be based on localisation
    private fun loadEvents(location: Location): List<EventEntity>?{
        var events: List<EventEntity>? = null

        viewModelScope.launch(Dispatchers.IO) {
            try {
                eventRepo.getEventWithQuery("dsa")
            }
            catch(ex: Exception){
                Log.e("Fetching events error","Some error happend while fetching events!")
            }
        }
        return events
    }



}