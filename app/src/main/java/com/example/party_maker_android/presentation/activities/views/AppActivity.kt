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

        setMenuItemClickListener()
    }

    override fun onResume() {
        super.onResume()
        mapBinding.bottomNavView.background = null
        mapBinding.bottomNavView.menu.getItem(2).isEnabled = false
        var selectedItemId = mapBinding.bottomNavView.selectedItemId
        setFragmentContainerContent(getFragment(selectedItemId))
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
            setFragmentContainerContent(getFragment(it.itemId))
            true
        }
    }

    private fun getFragment(iconId: Int): Fragment{
        return when(iconId){
            R.id.homeIcon -> {
                return MapFragment.newInstance()
            }
            R.id.searchIcon -> {
                return SearchEventsFragment.newInstance()
            }
            R.id.messagesIcon -> {
                return MessagesFragment.newInstance()
            }
            R.id.profileIcon -> {
                return ProfileFragment.newInstance()
            }
            else -> {
                return MapFragment.newInstance()
            }
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