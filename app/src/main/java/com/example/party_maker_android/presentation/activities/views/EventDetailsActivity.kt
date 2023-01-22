package com.example.party_maker_android.presentation.activities.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityEventDetailsBinding
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.presentation.activities.viewModels.EventDetailsViewModel
import com.example.party_maker_android.presentation.adapters.ParticipantsAdapter
import java.text.SimpleDateFormat

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
        setViewListeners()
    }

    private fun setViewModelObservers(){
        eventDetailsViewModel.event.observe(this){
            if(it != null){
                binding.eventDetailsName.text = it.name
                binding.eventDetailsDescription.text = it.description
                if(it.photo != null){
                    binding.eventDetailsImage.setImageBitmap(Base64Helper.getBitmapFromBase64(it.photo!!))
                }
                var dateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
                binding.eventDate.text = dateFormat.format(it?.date)
            }
        }
        eventDetailsViewModel.eventParticipants.observe(this){
            if(it == null || it.isEmpty()){
                binding.participantsText.text = "Event does not have participants, be first!"
                binding.userLoading.visibility = ProgressBar.INVISIBLE
            }
            else if(it.isNotEmpty()){
                binding.userLoading.visibility = ProgressBar.INVISIBLE
                var adapter = ParticipantsAdapter(this, it)
                binding.participantsList.adapter = adapter
            }
        }
        eventDetailsViewModel.currentUser.observe(this){
            //TODO - disable participate button depending on user
            if(it != null){
                if(it.id == eventDetailsViewModel.event.value?.organizaerId){
                    binding.participateButton.isEnabled = false
                }
                else if(eventDetailsViewModel.event.value?.participatorsIds?.contains(it.id)!!){
                    binding.participateButton.isEnabled = false
                }
            }
        }
    }

    private fun setViewListeners(){
        binding.placeTextPlaceholder.setOnClickListener {
            if(eventDetailsViewModel.event.value != null){
                //TODO - launch google maps
            }
        }
        binding.participateButton.setOnClickListener {
            try {
                eventDetailsViewModel.participateInEvent()
                binding.participateButton.isEnabled = false
            }
            catch(error: Error){
                Log.e(TAG, "Couldn't participate!")
            }

        }
    }
}