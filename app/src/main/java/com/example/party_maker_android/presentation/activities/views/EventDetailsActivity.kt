package com.example.party_maker_android.presentation.activities.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.databinding.ActivityEventDetailsBinding
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.presentation.activities.viewModels.EventDetailsViewModel
import com.example.party_maker_android.presentation.adapters.ParticipantsAdapter
import java.text.SimpleDateFormat
import java.util.*

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
            if(eventDetailsViewModel.currentUser.value?.id == it.organizaerId){
                binding.participateButton.text = "You organize this event!"
                binding.participateButton.isEnabled = false
            }
            else if(it.participatorsIds?.contains(eventDetailsViewModel.currentUser.value?.id) == true){
                binding.participateButton.text = "You participate!"
                binding.participateButton.isEnabled = false
            }
        }
        eventDetailsViewModel.eventParticipants.observe(this){
            if(eventDetailsViewModel.currentUser.value?.id == eventDetailsViewModel.event.value?.organizaerId){
                binding.participantsText.text = "It's your event, c'mon invite someone!"
                binding.userLoading.visibility = ProgressBar.INVISIBLE
            }
            else if(it == null || it.isEmpty()){
                binding.participantsText.text = "Event does not have participants, be first!"
                binding.userLoading.visibility = ProgressBar.INVISIBLE
            }
            else if(it.isNotEmpty()){
                binding.userLoading.visibility = ProgressBar.INVISIBLE
                var adapter = ParticipantsAdapter(this, it)
                binding.participantsList.adapter = adapter
            }
        }
    }

    private fun setViewListeners(){
        binding.showOnMapButton.setOnClickListener {
            var event: EventEntity? = eventDetailsViewModel.event.value
            if(event != null){
                val uri: String = "http://maps.google.com/maps?q=${event.latitude},${event.longitude}(${event.name})&iwloc=A&hl=es";
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                this.startActivity(intent)
            }
        }
        binding.participateButton.setOnClickListener {
            try {
                //TODO - make it work!
                eventDetailsViewModel.participateInEvent()
                binding.participateButton.text = "You participate!"
            }
            catch(error: Error){
                Log.e(TAG, "Couldn't participate!")
            }
        }
        binding.shareButton.setOnClickListener {
            ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle("Chooser title")
                .setText("http://play.google.com/store/apps/details?id=" + this.packageName)
                .startChooser();
        }
    }
}