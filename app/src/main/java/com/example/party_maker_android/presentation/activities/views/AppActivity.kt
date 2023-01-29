package com.example.party_maker_android.presentation.activities.views

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
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
    private var activeFragmentId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapBinding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(mapBinding.root)
        mapModel = ViewModelProvider(this)[AppViewModel::class.java]
        var selectedItem = mapBinding.bottomNavView.selectedItemId
        var fragment = getFragment(selectedItem)
        setFragmentContainerContent(fragment)
        setMenuItemClickListener()
    }

    override fun onResume() {
        super.onResume()
        mapBinding.bottomNavView.background = null
        mapBinding.bottomNavView.menu.getItem(2).isEnabled = false
    }


    private fun setMenuItemClickListener() {
        val myBottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        mapBinding.fab.setOnClickListener {
            var fragmentToAdd = AddEventFragment()
            myBottomNavigationView.selectedItemId = R.id.placeholder
            activeFragmentId = fragmentToAdd.id
            setFragmentContainerContent(fragmentToAdd)
        }

        //Here we should add more fragments to launch
        myBottomNavigationView.setOnItemSelectedListener {
            Log.i("MenuClicked", "Menu item clicked: ${it.itemId}")
            activeFragmentId = it.itemId
            setFragmentContainerContent(getFragment(activeFragmentId!!))
            true
        }
    }

    private fun getFragment(iconId: Int): Fragment{
        var fragmentManager = supportFragmentManager
        var fragment = fragmentManager.findFragmentById(iconId)

        if(fragment != null){
            return fragment
        }

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
            R.id.placeholder -> {
                return AddEventFragment.newInstance()
            }
            else -> {
                return MapFragment.newInstance()
            }
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true);
    }

    private fun setFragmentContainerContent(fragmentToAdd: Fragment){
        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(mapBinding.fragmentContainer.id, fragmentToAdd)
        transaction.commit()
    }

}