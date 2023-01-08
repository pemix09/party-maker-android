package com.example.party_maker_android.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.FragmentMapBinding
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView

class MapFragment : Fragment() {
    private lateinit var viewModel: MapViewModel
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: MapView
    val REQUEST_MAP_PERMISSIONS_REQUEST_CODE = 100
    private var mapController: MapController? = null
        get() = map?.controller as MapController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        map = activity?.findViewById(R.id.map)!!
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val permissionsToRequest = ArrayList<String>()
        var i = 0
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i])
            i++
        }
        if (permissionsToRequest.size > 0) {
            requestPermissions(
                permissionsToRequest.toTypedArray(),
                REQUEST_MAP_PERMISSIONS_REQUEST_CODE)
        }
    }


    companion object {
        fun newInstance() = MapFragment()
    }

}