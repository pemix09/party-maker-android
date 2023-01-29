package com.example.party_maker_android.domain.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat

class LocationService(private val context: Context) {
    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val hasGps
        get() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    private val hasNetwork
        get() = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    fun setLocationListener(listener: LocationListener) {
        return requestLocation(listener)
    }

    private fun requestLocation(locationListener: LocationListener){
        if (hasGps) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                var message = "Locations permisons are not granted!"
                Log.e("Permision error:",message)
                throw Error(message)
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10f, locationListener);
        }
        else if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 5000, 10f, locationListener);
        }
    }
}