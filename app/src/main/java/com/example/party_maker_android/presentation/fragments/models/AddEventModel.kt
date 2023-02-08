package com.example.party_maker_android.presentation.fragments.models

import android.content.Context
import android.location.Location
import android.location.LocationListener
import androidx.lifecycle.MutableLiveData
import com.example.party_maker_android.domain.services.LocationService

class AddEventModel(context: Context) : LocationListener {
    private var locationService = LocationService(context)
    var location = MutableLiveData<Location>()

    init {
        locationService.setLocationListener(this)
    }
    override fun onLocationChanged(loc: Location) {
        location.value = loc
    }

    fun refresh(){}
}