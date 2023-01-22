package com.example.party_maker_android.presentation.activities.views

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityMapBinding
import com.example.party_maker_android.presentation.activities.viewModels.AppViewModel
import com.example.party_maker_android.presentation.fragments.views.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.util.BoundingBox


class AppActivity : AppCompatActivity() {
    lateinit var mapBinding: ActivityMapBinding
    lateinit var mapModel: AppViewModel
    private val TAG = "mapActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //close the app if location is not granted - it's base of the app
        if(isLocationPermissionGranted() == false){
            this.finishAffinity()
        }

        mapBinding = ActivityMapBinding.inflate(layoutInflater)

        setContentView(mapBinding.root)
        mapModel = ViewModelProvider(this)[AppViewModel::class.java]

        setInitialMenuState()
        setMenuItemClickListener()
    }

    //Navigate to profile, to show user's events
    fun onNewEventCreated(){
        val profileFragment = ProfileFragment()
        setFragmentContainerContent(profileFragment)
    }

    //when we change location of the map
    /*fun changeMapLocation(boundingBox: BoundingBox){
        mapModel.updateLocation(boundingBox)
    }*/

    private fun setInitialMenuState(){
        mapBinding.bottomNavView.background = null
        mapBinding.bottomNavView.menu.getItem(2).isEnabled = false
        val mapFragment = MapFragment.newInstance()
        setFragmentContainerContent(mapFragment)
    }

    private fun setMenuItemClickListener() {
        val myBottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        mapBinding.fab.setOnClickListener {
            var fragmentToAdd = AddEventFragment()
            setFragmentContainerContent(fragmentToAdd)
            mapBinding.bottomNavView.selectedItemId = R.id.placeholder
        }

        //Here we should add more fragments to launch
        myBottomNavigationView.setOnItemSelectedListener {
            Log.i("MenuClicked", "Menu item clicked: ${it.itemId}")
            when(it.itemId){
                R.id.homeIcon -> {
                    val mapFragment = MapFragment.newInstance()
                    setFragmentContainerContent(mapFragment)
                }
                R.id.searchIcon -> {
                    var searchFragment = SearchEventsFragment.newInstance()
                    setFragmentContainerContent(searchFragment)
                }
                R.id.messagesIcon -> {
                    val messagesFragment = MessagesFragment.newInstance()
                    setFragmentContainerContent(messagesFragment)
                }
                R.id.profileIcon -> {
                    val profileFragment = ProfileFragment.newInstance()
                    setFragmentContainerContent(profileFragment)
                }
            }
            true
        }
    }

    private fun setFragmentContainerContent(fragmentToAdd: Fragment){
        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(mapBinding.fragmentContainer.id, fragmentToAdd)
        transaction.commit()
    }

    private fun isLocationPermissionGranted(): Boolean {
        var requestCode = 100
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                requestCode
            )
            false
        } else {
            true
        }
    }

}