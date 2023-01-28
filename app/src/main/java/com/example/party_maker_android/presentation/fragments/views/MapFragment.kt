package com.example.party_maker_android.presentation.fragments.views

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.databinding.FragmentMapBinding
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.presentation.fragments.viewModels.MapViewModel
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment(), Marker.OnMarkerClickListener, IMyLocationConsumer {
    private val TAG = "MapFragment"
    private lateinit var viewModel: MapViewModel
    private lateinit var binding: FragmentMapBinding
    private lateinit var map: MapView
    private lateinit var locationOverlay: MyLocationNewOverlay

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //from somDroid configuration provider
        getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        setUpMap()
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        viewModel.setContext(requireContext())
        setViewModelObservers()
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

    private fun setViewModelObservers(){
        viewModel.eventsToShow.observe(viewLifecycleOwner){
            for(event in it){
                val location = GeoPoint(event.latitude!!, event.longitude!!)
                val eventMarker = Marker(map)
                eventMarker.position = location
                eventMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                eventMarker.title = event.name
                eventMarker.subDescription = event.description

                if(event.photo != null && event?.photo?.isNotEmpty()!!){
                    try{
                        var originalBitmap = Base64Helper.getBitmapFromBase64(event.photo!!)
                        eventMarker.image = BitmapDrawable(originalBitmap)
                    }
                    catch(error: Error){
                        Log.e(TAG, "Cannot set icon for marker, default is set")
                    }
                }
                map.overlays.add(eventMarker)
            }

            Log.i(TAG, "Success of fetching events!")
        }
    }
    private fun setViewListeners(){
        binding.navigateToCurrentLocation.setOnClickListener {
            map.controller.animateTo(locationOverlay.myLocation)
        }
        binding.fetchForCurrentLocationButton.setOnClickListener {
            viewModel.getEventsForBoundingBox(map.boundingBox)
        }
    }
    private fun setUpMap(){
        setViewListeners()
        map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        locationOverlay.enableMyLocation()
        locationOverlay.enableFollowLocation()
        locationOverlay.isOptionsMenuEnabled = true;
        map.setMultiTouchControls(true);
        map.overlays.add(locationOverlay)
        map.controller.setZoom(15)

    }

    companion object {
        fun newInstance() = MapFragment()
    }

    override fun onMarkerClick(marker: Marker?, mapView: MapView?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location?, source: IMyLocationProvider?) {
        Log.i(TAG, "Success: ${location.toString()}")
    }

}