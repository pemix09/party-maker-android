package com.example.party_maker_android.presentation.activities.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.ActivityEventConversationBinding
import com.example.party_maker_android.domain.repositories.UserRepository
import com.example.party_maker_android.presentation.activities.viewModels.EventConversationViewModel
import com.example.party_maker_android.presentation.adapters.ConversationAdapter
import com.example.party_maker_android.presentation.adapters.EventsAdapter

class EventConversationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventConversationBinding
    private lateinit var viewModel: EventConversationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventConversationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[EventConversationViewModel::class.java]
        downloadEventConversation()
        setViewModelObservers()
        setViewObservers()
    }

    private fun downloadEventConversation(){
        var eventId: Long = (intent.extras?.get("eventId") as Long?)!!

        if(eventId != null){
            var eventIdAsInt = eventId.toInt()
            viewModel.refreshEventConversation(eventIdAsInt)
        }
    }

    private fun setViewModelObservers(){
        viewModel.eventConversation.observe(this){
            var adapter = this?.let { ctx -> ConversationAdapter(ctx, it, viewModel.user.value?.id!!) }
            binding.conversation.adapter = adapter
        }
    }

    private fun setViewObservers(){
        binding.messageTextInput.addTextChangedListener {
            binding.sendMessageButton.isEnabled = it.toString().isNotEmpty()
        }
        binding.sendMessageButton.setOnClickListener {
            viewModel.sendMessage(binding.messageTextInput.text.toString())
            binding.messageTextInput.text = null
        }
    }
}