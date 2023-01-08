package com.example.party_maker_android.ui.App

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityMapBinding
import com.example.party_maker_android.ui.fragments.AddEventFragment
import com.example.party_maker_android.ui.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class AppActivity : AppCompatActivity() {
    lateinit var mapBinding: ActivityMapBinding
    lateinit var mapModel: AppModel
    private val TAG = "mapActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //close the app if location is not granted - it's base of the app
        if(isLocationPermissionGranted() == false){
            this.finishAffinity()
        }

        mapBinding = ActivityMapBinding.inflate(layoutInflater)

        setContentView(mapBinding.root)
        mapModel = ViewModelProvider(this)[AppModel::class.java]

        setInitialMenuState()
        setMenuItemClickListener()
    }

    //Navigate to profile, to show user's events
    fun onNewEventCreated(){
        val profileFragment = ProfileFragment()
        setFragmentContainerContent(profileFragment)
    }

    private fun setInitialMenuState(){
        mapBinding.bottomNavView.background = null
        mapBinding.bottomNavView.menu.getItem(2).isEnabled = false
    }

    private fun setMenuItemClickListener() {
        val myBottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        mapBinding.fab.setOnClickListener {
            var fragmentToAdd = AddEventFragment()
            setFragmentContainerContent(fragmentToAdd)
        }

        //Here we should add more fragments to launch
        myBottomNavigationView.setOnItemSelectedListener {
            Log.i("MenuClicked", "Menu item clicked: ${it.itemId}")
            when(it.itemId){
                R.id.homeIcon -> {
                }
                R.id.searchIcon -> {

                }
                R.id.messagesIcon -> {

                }
                R.id.settingsIcon -> {

                }
                else -> {
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