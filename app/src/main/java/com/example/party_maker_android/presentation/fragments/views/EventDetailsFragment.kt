package com.example.party_maker_android.presentation.fragments.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.party_maker_android.R
import com.example.party_maker_android.presentation.fragments.models.EventDetailsViewModel

class EventDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = EventDetailsFragment()
    }

    private lateinit var viewModel: EventDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}