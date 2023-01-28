package com.example.party_maker_android.presentation.fragments.views

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.FragmentMessagesBinding
import com.example.party_maker_android.presentation.activities.views.EventConversationActivity
import com.example.party_maker_android.presentation.adapters.EventsAdapter
import com.example.party_maker_android.presentation.fragments.viewModels.MessagesViewModel

class MessagesFragment : Fragment() {

    companion object {
        fun newInstance() = MessagesFragment()
    }

    private lateinit var viewModel: MessagesViewModel
    private lateinit var binding: FragmentMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessagesBinding.inflate(inflater, container, false)
        setViewObservers()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MessagesViewModel::class.java)
        viewModel.setContext(requireContext())
        viewModel.refreshData()
        setViewModelObservers()
    }

    private fun setViewModelObservers(){
        viewModel.eventToShow.observe(viewLifecycleOwner){
            binding.eventMessagesLoadingBar.visibility = ProgressBar.INVISIBLE
            if(it != null && it.isNotEmpty()){
                var adapter = context?.let { ctx -> EventsAdapter(ctx, it) }
                binding.eventsToShowForMessagesList.adapter = adapter
            }
            else{
                binding.noEventsToShowMessage.visibility = TextView.VISIBLE
            }
        }
    }

    private fun setViewObservers(){
        binding.eventsToShowForMessagesList.setOnItemClickListener { adapterView, view, index, id ->
            var eventConversationIntent = Intent(activity, EventConversationActivity::class.java)
            eventConversationIntent.putExtra("eventId", id)
            this.startActivity(eventConversationIntent)
        }
    }

}