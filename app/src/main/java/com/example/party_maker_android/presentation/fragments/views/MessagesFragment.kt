package com.example.party_maker_android.presentation.fragments.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.FragmentMessagesBinding
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
        //TODO set view observers
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
        viewModel.messagesToShow.observe(viewLifecycleOwner){
            //TODO - show messages on fragment
        }
    }

}