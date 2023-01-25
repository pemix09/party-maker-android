package com.example.party_maker_android.presentation.fragments.views

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.FragmentAddEventBinding
import com.example.party_maker_android.presentation.activities.views.AppActivity
import com.example.party_maker_android.presentation.fragments.viewModels.AddEventViewModel

class AddEventFragment : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = AddEventFragment()
    }
    private val TAG = "AddEventFragment"
    private lateinit var fragmentActivityContext: AppActivity
    private lateinit var viewModel: AddEventViewModel
    private lateinit var binding: FragmentAddEventBinding
    private val requestLocationCode = 10000
    private val requestCoarseLocation = 20000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(activity: Activity) {
        fragmentActivityContext = activity as AppActivity
        super.onAttach(activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), requestLocationCode)
        }
        else if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), requestCoarseLocation)
        }

        viewModel = ViewModelProvider(this).get(AddEventViewModel::class.java).also {
            it.setContext(fragmentActivityContext)
        }

        ArrayAdapter.createFromResource(fragmentActivityContext, R.array.music_genre_array, android.R.layout.simple_spinner_item)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = it
                binding.spinner.onItemSelectedListener = this
            }
        setModelObservers()
        setViewChangeListeners()
        setAddEventAction()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){

    }

    override fun onNothingSelected(parent: AdapterView<*>){

    }
    fun addSelectedPhoto(base64Photo: String){
        viewModel.photo = base64Photo
    }

    fun showTimePickerDialog(view: View){
        Log.i(TAG, "date picker was clicked!")
    }

    private fun setModelObservers(){
        viewModel.location.observe(viewLifecycleOwner){
            if(it != null){
                binding.currentLocationText.text = "longitude: ${it.longitude}, latitude: ${it.longitude}"
            }
            else{
                binding.currentLocationText.text = "Couldn't determine location"
            }
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
        viewModel.isFormValid.observe(viewLifecycleOwner){
            binding.addEventButton.isEnabled = it
        }

    }

    private fun setViewChangeListeners(){
        binding.descriptionInput.addTextChangedListener {
            viewModel.description = it.toString()
        }
        binding.nameInput.addTextChangedListener {
            viewModel.name = it.toString()
        }
        binding.placeInput.addTextChangedListener {
            viewModel.place = it.toString()
            if(it.toString().isEmpty()){
                binding.placeInputContainer.helperText = "Place cannot be empty!"
            }
            else{
                binding.placeInputContainer.helperText = null
            }
        }
    }


    private fun setAddEventAction(){
        binding.addEventButton.setOnClickListener {
            viewModel.addEvent()
        }
    }

}