package com.example.party_maker_android.ui.map

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityMapBinding
import com.example.party_maker_android.ui.fragments.AddEventFragment

class MapActivity : AppCompatActivity() {
    lateinit var mapBinding: ActivityMapBinding
    lateinit var mapModel: MapModel
    private val TAG = "mapActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //close the app if location is not granted - it's base of the app
        if(isLocationPermissionGranted() == false){
            this.finishAffinity()
        }

        mapBinding = ActivityMapBinding.inflate(layoutInflater)

        setContentView(mapBinding.root)
        mapModel = ViewModelProvider(this)[MapModel::class.java]

        mapBinding.bottomNavView.background = null
        mapBinding.bottomNavView.menu.getItem(2).isEnabled = false

        setMenuItemClickListener()
    }

    private fun setMenuItemClickListener() {
        mapBinding.fab.setOnClickListener {
            var fragmentToAdd = AddEventFragment()
            setFragmentContainerContent(fragmentToAdd)
        }
/*        mapBinding.bottomNavView.setOnClickListener {
            Log.i(TAG, "Clicked, kurde faja")
        }

        mapBinding.bottomMapMenu.setOnMenuItemClickListener {
            Log.i("MenuClicked", "Menu item clicked: ${it.itemId}")
            when(it.itemId){
                R.id.homeIcon -> {
                }
                R.id.searchIcon -> {
                    var fragmentToAdd = AddEventFragment()
                    setFragmentContainerContent(fragmentToAdd)
                }
                R.id.placeholder -> {
                    var fragmentToAdd = AddEventFragment()
                    setFragmentContainerContent(fragmentToAdd)
                }
                R.id.messagesIcon -> {

                }
                R.id.settingsIcon -> {

                }
                else -> {
                }
            }
            return@setOnMenuItemClickListener true
        }*/
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