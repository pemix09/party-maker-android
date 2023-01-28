package com.example.party_maker_android.presentation.fragments.views

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.databinding.FragmentProfileBinding
import com.example.party_maker_android.presentation.activities.views.EventDetailsActivity
import com.example.party_maker_android.presentation.activities.views.WelcomeActivity
import com.example.party_maker_android.presentation.adapters.EventsAdapter
import com.example.party_maker_android.presentation.fragments.viewModels.ProfileViewModel
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val TAG = "ProfileFragment"
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private val photoGalleryRequestCode: Int = 1
    private val pickPhotoRequestCode: Int = 2
    private val requestCameraAccessRequestCode: Int = 3
    private val makePhotoRequestCode: Int = 4


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setViewObservers()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.setContext(requireContext())
        setViewModelObservers()
    }

    private fun setViewObservers(){
        binding.eventList.setOnItemClickListener { adapterView, view, index, id ->
            var eventDetailsIntent = Intent(activity, EventDetailsActivity::class.java)
            eventDetailsIntent.putExtra("EventId", id.toInt())
            context?.startActivity(eventDetailsIntent)
        }
        binding.saveChangesButton.setOnClickListener {
            try{
                viewModel.updateUser()
                binding.saveChangesButton.visibility = Button.GONE
            }
            catch(error: Error){

            }
        }
        binding.profileImage.setOnClickListener {
            binding.saveChangesButton.visibility = Button.VISIBLE
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
        binding.eventsToReviewCard.setOnClickListener {
            //active to inactive
            if(viewModel.activeCard == it.id) {
                var color = resources.getColor(R.color.white)
                it.setBackgroundColor(color)
                showAllCards()
            }
            //inactive to active
            else{
                binding.eventsLoadingBar.visibility = ProgressBar.VISIBLE
                viewModel.setActiveCard(it.id)
                var color = resources.getColor(R.color.primary_color)
                it.setBackgroundColor(color)
                binding.yourEventsCard.visibility = MaterialCardView.GONE
                binding.participatedEventsCard.visibility = MaterialCardView.GONE
                binding.followedEventsCard.visibility = MaterialCardView.GONE
            }
        }
        binding.yourEventsCard.setOnClickListener {
            //active to inactive
            if(viewModel.activeCard == it.id) {
                var color = resources.getColor(R.color.white)
                it.setBackgroundColor(color)
                showAllCards()
            }
            //inactive to active
            else{
                binding.eventsLoadingBar.visibility = ProgressBar.VISIBLE
                viewModel.setActiveCard(it.id)
                var color = resources.getColor(R.color.primary_color)
                it.setBackgroundColor(color)
                binding.participatedEventsCard.visibility = MaterialCardView.GONE
                binding.followedEventsCard.visibility = MaterialCardView.GONE
                binding.eventsToReviewCard.visibility = MaterialCardView.GONE
                binding.yourEventsCard.visibility = MaterialCardView.VISIBLE
            }
        }

        binding.participatedEventsCard.setOnClickListener {
            //active to inactive
            if(viewModel.activeCard == it.id) {
                var color = resources.getColor(R.color.white)
                it.setBackgroundColor(color)
                showAllCards()
            }
            //inactive to active
            else{
                binding.eventsLoadingBar.visibility = ProgressBar.VISIBLE
                viewModel.setActiveCard(it.id)
                var color = resources.getColor(R.color.primary_color)
                it.setBackgroundColor(color)
                binding.participatedEventsCard.visibility = MaterialCardView.VISIBLE
                binding.followedEventsCard.visibility = MaterialCardView.GONE
                binding.eventsToReviewCard.visibility = MaterialCardView.GONE
                binding.yourEventsCard.visibility = MaterialCardView.GONE
            }
        }

        binding.followedEventsCard.setOnClickListener {
            //active to inactive
            if(viewModel.activeCard == it.id) {
                var color = resources.getColor(R.color.white)
                it.setBackgroundColor(color)
                showAllCards()
            }
            //inactive to active
            else{
                binding.eventsLoadingBar.visibility = ProgressBar.VISIBLE
                viewModel.setActiveCard(it.id)
                var color = resources.getColor(R.color.primary_color)
                it.setBackgroundColor(color)
                binding.participatedEventsCard.visibility = MaterialCardView.GONE
                binding.followedEventsCard.visibility = MaterialCardView.VISIBLE
                binding.eventsToReviewCard.visibility = MaterialCardView.GONE
                binding.yourEventsCard.visibility = MaterialCardView.GONE
            }
        }

        binding.logoutPlaceholder.setOnClickListener {
            viewModel.logout()
            var welcomeIntent = Intent(context, WelcomeActivity::class.java)
            activity?.startActivity(welcomeIntent)
        }
    }
    private fun setViewModelObservers(){
        viewModel.errorMessage.observe(viewLifecycleOwner){
            val toast = Toast.makeText(context, it.toString(), Toast.LENGTH_LONG)
            toast.show()
        }
        viewModel.currentUser.observe(viewLifecycleOwner){
            binding.profileUserName.text = it?.userName
            if(it?.photo?.isNotEmpty()!!){
                try {
                    binding.profileImage.setImageBitmap(Base64Helper.getBitmapFromBase64(it.photo!!))
                }
                catch(error: Error){
                    binding.profileImage.setImageResource(R.drawable.profile_icon)
                }
            }
            else{
                binding.profileImage.setImageResource(R.drawable.profile_icon)
            }
            binding.profileEmail.text = it?.email
            binding.profileRole.text = it?.role
            binding.ratingText.text = context?.getString(R.string.profile_rating_text, it?.avarageRating.toString())

            var dateFormat = SimpleDateFormat("MM.yyyy")
            var date = dateFormat.format(it?.registrationDate)
            binding.sinceText.text = context?.getString(R.string.registration_date_text, date)
        }
        viewModel.eventsToShow.observe(viewLifecycleOwner){
            if(it != null && it.isNotEmpty()){
                var adapter = context?.let { ctx -> EventsAdapter(ctx, it) }
                binding.eventList.adapter = adapter
                binding.eventsLoadingBar.visibility = ProgressBar.INVISIBLE
                binding.eventList.visibility = ListView.VISIBLE
                binding.noEventsText.visibility = TextView.GONE
            }
            else{
                binding.eventList.adapter = null
                binding.eventsLoadingBar.visibility = ProgressBar.INVISIBLE
                binding.eventList.visibility = ListView.INVISIBLE
                binding.noEventsText.visibility = TextView.VISIBLE
            }
            if(it == null){
                binding.noEventsText.visibility = TextView.INVISIBLE
            }
        }
    }

    private fun showAllCards(){
        binding.eventsToReviewCard.visibility = MaterialCardView.VISIBLE
        binding.yourEventsCard.visibility = MaterialCardView.VISIBLE
        binding.participatedEventsCard.visibility = MaterialCardView.VISIBLE
        binding.followedEventsCard.visibility = MaterialCardView.VISIBLE
        binding.eventsLoadingBar.visibility = ProgressBar.GONE
        binding.eventList.visibility = ListView.GONE
        viewModel.clearEventsToShow()
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
                    viewModel.changeProfilePicture(selectedBitMap)
                    //binding.profileImage.setImageBitmap(selectedBitMap)
                }
                else{
                    var selectedBitMap = MediaStore.Images.Media.getBitmap(context?.contentResolver!!, selectedPhoto)
                    viewModel.changeProfilePicture(selectedBitMap)
                    //binding.profileImage.setImageBitmap(selectedBitMap)
                }
            }
            //photo from camera
            else if(requestCode == makePhotoRequestCode && resultCode == Activity.RESULT_OK){
                var selectedBitMap = data.extras?.get("data") as Bitmap
                viewModel.changeProfilePicture(selectedBitMap)
                //binding.profileImage.setImageBitmap(selectedBitMap)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}