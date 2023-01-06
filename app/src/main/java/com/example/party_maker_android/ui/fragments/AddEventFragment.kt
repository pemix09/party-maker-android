package com.example.party_maker_android.ui.fragments

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.FragmentAddEventBinding

class AddEventFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = AddEventFragment()
    }
    private lateinit var fragmentContext: Context
    private lateinit var viewModel: AddEventViewModel
    private lateinit var binding: FragmentAddEventBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(activity: Activity) {
        fragmentContext = activity
        super.onAttach(activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEventViewModel::class.java).also {
            it.setContext(fragmentContext)
        }

        ArrayAdapter.createFromResource(fragmentContext, R.array.music_genre_array, android.R.layout.simple_spinner_item)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = it
                binding.spinner.onItemSelectedListener = this
            }
        setModelObservers()
        setViewChangeListeners()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){

    }

    override fun onNothingSelected(parent: AdapterView<*>){

    }

    private fun setModelObservers(){
        viewModel.location.observe(viewLifecycleOwner){
            binding.currentLocationText.text = "longitude: ${it.longitude}, latitude: ${it.longitude}"
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            binding.addEventMessage.text = it.toString()
        }
        viewModel.descriptionValidationMessage.observe(viewLifecycleOwner){
            binding.descriptionInputContainer.helperText = it
        }
        viewModel.nameValidationMessage.observe(viewLifecycleOwner){
            binding.nameInputContainer.helperText = it
        }
    }

    private fun setViewChangeListeners(){
        binding.descriptionInput.addTextChangedListener {
            viewModel.description = it.toString()
        }
        binding.nameInput.addTextChangedListener {
            viewModel.name = it.toString()
        }
    }
}