package com.example.party_maker_android.presentation.fragments.views

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
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
    private val photoGalleryRequestCode: Int = 1312
    private val pickPhotoRequestCode: Int = 233
    private val requestCameraAccessRequestCode: Int = 3435
    private val makePhotoRequestCode: Int = 4534

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(activity: Activity) {
        fragmentActivityContext = activity as AppActivity
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), requestLocationCode)
        }
        else if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), requestCoarseLocation)
        }

        viewModel = ViewModelProvider(this).get(AddEventViewModel::class.java)
        super.onAttach(activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.photo = savedInstanceState?.getString("photo")
        viewModel.name = savedInstanceState?.getString("name")
        Log.i(TAG, "values got back! ${viewModel.photo}")

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

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "photo : ${viewModel?.photo} name: ${viewModel?.name}")
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){

    }

    override fun onNothingSelected(parent: AdapterView<*>){

    }
    fun addSelectedPhoto(base64Photo: String){
        viewModel?.photo = base64Photo
    }

    fun showTimePickerDialog(view: View){
        Log.i(TAG, "date picker was clicked!")
    }

    private fun setModelObservers(){
        viewModel?.location?.observe(viewLifecycleOwner){
            if(it != null){
                binding.currentLocationText.text = "longitude: ${it.longitude}, latitude: ${it.longitude}"
            }
            else{
                binding.currentLocationText.text = "Couldn't determine location"
            }
        }
        viewModel?.errorMessage?.observe(viewLifecycleOwner){
            binding.addEventMessage.text = it.toString()
        }
        viewModel?.descriptionValidationMessage?.observe(viewLifecycleOwner){
            binding.descriptionInputContainer.helperText = it
        }
        viewModel?.nameValidationMessage?.observe(viewLifecycleOwner){
            binding.nameInputContainer.helperText = it
        }
        viewModel?.isFormValid?.observe(viewLifecycleOwner){
            binding.addEventButton.isEnabled = it
        }

    }

    private fun setViewChangeListeners(){
        binding.descriptionInput.addTextChangedListener {
            viewModel?.description = it.toString()
        }
        binding.nameInput.addTextChangedListener {
            viewModel?.name = it.toString()
        }
        binding.placeInput.addTextChangedListener {
            viewModel?.place = it.toString()
            if(it.toString().isEmpty()){
                binding.placeInputContainer.helperText = "Place cannot be empty!"
            }
            else{
                binding.placeInputContainer.helperText = null
            }
        }
        binding.addEventPhotoButton.setOnClickListener {
            var options = arrayOf("gallery", "camera")
            var dialog = AlertDialog.Builder(context)
            dialog.setTitle("Choose image source:")
            dialog.setItems(options, DialogInterface.OnClickListener { dialogInterface, index ->
                if(options[index] == "gallery"){
                    if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), photoGalleryRequestCode)
                    }
                    else{
                        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(pickPhoto, pickPhotoRequestCode)
                    }
                }
                else if(options[index] == "camera"){
                    if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){
                        requestPermissions(arrayOf(android.Manifest.permission.CAMERA), requestCameraAccessRequestCode)
                    }
                    else{
                        val openCamera: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(openCamera, makePhotoRequestCode)
                    }
                }
            })
            dialog.show()
        }
    }


    private fun setAddEventAction(){
        binding.addEventButton.setOnClickListener {
            viewModel?.addEvent()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == photoGalleryRequestCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, pickPhotoRequestCode)
            }
        }
        else if(requestCode == requestCameraAccessRequestCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val openCamera: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(openCamera, makePhotoRequestCode)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null){
            if(requestCode == pickPhotoRequestCode && resultCode == Activity.RESULT_OK){
                var selectedPhoto = data.data
                if(Build.VERSION.SDK_INT >= 28){
                    val source = ImageDecoder.createSource(context?.contentResolver!!, selectedPhoto!!)
                    var selectedBitMap = ImageDecoder.decodeBitmap(source)
                    try{
                        viewModel?.addEventPhoto(selectedBitMap)
                    }
                    catch (error: Error){
                        Log.e(TAG, "cannot update photo!")
                    }
                    binding.eventPhoto.setImageBitmap(selectedBitMap)
                }
                else{
                    var selectedBitMap = MediaStore.Images.Media.getBitmap(context?.contentResolver!!, selectedPhoto)
                    try{
                        viewModel?.addEventPhoto(selectedBitMap)
                    }
                    catch (error: Error){
                        Log.e(TAG, "cannot update photo!")
                    }
                    binding.eventPhoto.setImageBitmap(selectedBitMap)
                }
            }
            //photo from camera
            else if(requestCode == makePhotoRequestCode && resultCode == Activity.RESULT_OK){
                var selectedBitMap = data.extras?.get("data") as Bitmap
                try{
                    viewModel?.addEventPhoto(selectedBitMap)
                    binding.eventPhoto.setImageBitmap(selectedBitMap)
                }
                catch (error: Error){
                    Log.e(TAG, "cannot update photo!")
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i(TAG, "Saving fragment!")
        outState.putString("photo", viewModel?.photo)
        outState.putString("name", viewModel?.name)
        super.onSaveInstanceState(outState)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(viewModel != null){

        }
    }
}