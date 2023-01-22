package com.example.party_maker_android.presentation.activities.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityEventDetailsBinding
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.presentation.activities.viewModels.EventDetailsViewModel

class EventDetailsActivity : AppCompatActivity() {
    private val TAG = "EventDetailsActivity"
    private lateinit var binding: ActivityEventDetailsBinding
    private lateinit var eventDetailsViewModel: EventDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        eventDetailsViewModel = ViewModelProvider(this)[EventDetailsViewModel::class.java]

        var extras = intent.extras
        if(extras != null){
            var eventId = extras.getInt("EventId")
            eventDetailsViewModel.saveEventIdAndFetch(eventId)
        }
        else{
            Log.e(TAG, "Event id hasnt been passed!")
            throw Error("event id hasnt been passed!")
        }
        setViewModelObservers()
    }

    private fun setViewModelObservers(){
        eventDetailsViewModel.event.observe(this){
            if(it != null){
                binding.eventDetailsName.text = it.name
                binding.eventDetailsDescription.text = it.description
                if(it.photo != null){
                    binding.eventDetailsImage.setImageBitmap(Base64Helper.getBitmapFromBase64(it.photo!!))
                }
            }
        }
    }

    private fun setViewListeners(){
        binding.placeTextPlaceholder.setOnClickListener {
            if(eventDetailsViewModel.event.value != null){

            }
        }
    }
}