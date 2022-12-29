package com.example.party_maker_android.ui.map

import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import com.example.party_maker_android.models.EventEntity

class MapModel(application: Application): AndroidViewModel(application) {

    private lateinit var events: List<EventEntity>

    //TODO - get location!
    /*lateinit var locationManager = getSystemService(application.LOCATION_SERVICE) as LocationManager
    private lateinit var location: Location

    //first loading of events, should be based on localisation
    private loadEvents(string location){

    }


    private getLocation(): Location{

        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }
        else if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkLocationListener
            )
        }
    }*/


}