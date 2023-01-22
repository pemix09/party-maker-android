package com.example.party_maker_android.presentation.fragments.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.FragmentEventListBinding
import com.example.party_maker_android.presentation.adapters.EventsAdapter
import com.example.party_maker_android.presentation.fragments.viewModels.EventListViewModel

class EventListFragment : Fragment() {

    companion object {
        fun newInstance() = EventListFragment()
    }

    private lateinit var viewModel: EventListViewModel
    private lateinit var binding: FragmentEventListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventListBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventListViewModel::class.java)
        setViewModelObservers()
        viewModel.setContext(requireContext())
        viewModel.getEvents(arguments?.getInt("Type")!!)
    }

    private fun setViewModelObservers(){
        viewModel.eventsToShow.observe(viewLifecycleOwner){
            binding.fetchingEventsProgressBar.visibility = ProgressBar.INVISIBLE
            if(it.isNotEmpty()){
                binding.eventListMessage.visibility = TextView.INVISIBLE
                var adapter = EventsAdapter(requireContext(), it)
                binding.eventListItems.adapter = adapter
            }
            else{
                binding.eventListMessage.visibility = TextView.VISIBLE
                binding.eventListMessage.text = "You don't have any events to review!"
            }
        }
    }

}