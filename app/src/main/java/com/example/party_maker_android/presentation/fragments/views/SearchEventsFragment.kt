package com.example.party_maker_android.presentation.fragments.views

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.FragmentSearchEventsBinding
import com.example.party_maker_android.presentation.activities.views.EventDetailsActivity
import com.example.party_maker_android.presentation.adapters.EventsAdapter
import com.example.party_maker_android.presentation.fragments.viewModels.SearchEventsViewModel

class SearchEventsFragment : Fragment() {

    companion object {
        fun newInstance() = SearchEventsFragment()
    }

    private lateinit var viewModel: SearchEventsViewModel
    private lateinit var binding: FragmentSearchEventsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchEventsBinding.inflate(inflater)
        setViewListeners()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchEventsViewModel::class.java)
        viewModel.setContext(requireContext())
        setViewModelObservers()
    }

    private fun setViewModelObservers(){
        viewModel.searchResult.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                var adapter = EventsAdapter(requireContext(), it)
                binding.searchedEventsList.adapter = adapter
            }
            else{
                binding.searchMessage.text = "No events found!"
            }
        }
        viewModel.searchMessage.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                binding.searchMessage.text = it.toString()
            }
        }
    }

    private fun setViewListeners(){
        binding.searchEventsTextInput.addTextChangedListener {
            binding.searchMessage.text = null
            if(it.toString().isNotEmpty()){
                viewModel.search(it.toString())
            }
        }
        binding.searchedEventsList.setOnItemClickListener { adapterView, view, index, id ->
            var eventDetailsIntent = Intent(activity, EventDetailsActivity::class.java)
            eventDetailsIntent.putExtra("EventId", id.toInt())
            context?.startActivity(eventDetailsIntent)
        }
    }
}