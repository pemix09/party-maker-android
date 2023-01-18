package com.example.party_maker_android.presentation.fragments.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.databinding.FragmentProfileBinding
import com.example.party_maker_android.presentation.adapters.EventsAdapter
import com.example.party_maker_android.presentation.fragments.viewModels.ProfileViewModel
import java.text.SimpleDateFormat

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val TAG = "ProfileFragment"
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.setContext(requireContext())
        setViewModelObservers()
    }

    private fun setViewModelObservers(){
        viewModel.errorMessage.observe(viewLifecycleOwner){
            val toast = Toast.makeText(context, it.toString(), Toast.LENGTH_LONG)
            toast.show()
        }
        viewModel.currentUser.observe(viewLifecycleOwner){
            binding.profileUserName.text = it?.userName
            if(it?.photo != null){
                binding.profileImage.setImageBitmap(Base64Helper.getBitmapFromBase64(it.photo!!))
            }
            binding.profileEmail.text = it?.email
            binding.profileRole.text = it?.role
            binding.ratingText.text = context?.getString(R.string.profile_rating_text, it?.avarageRating.toString())

            var dateFormat = SimpleDateFormat("MM.yyyy")
            var date = dateFormat.format(it?.registrationDate)
            binding.sinceText.text = context?.getString(R.string.registration_date_text, date)
        }
        viewModel.organizedEvents.observe(viewLifecycleOwner){ organizedEvents ->
            if(organizedEvents != null && organizedEvents.isNotEmpty()){
                var adapter = context?.let { ctx -> EventsAdapter(ctx, organizedEvents) }
                binding.organizedEventsList.adapter = adapter
                binding.organizedEventsList.setOnItemClickListener { adapterView, view, index, id ->
                    //TODO - start new fragment or activity with event details
                }
            }
            else{
                binding.emptyOrganizedEventsText.visibility = TextView.VISIBLE
            }
        }
        viewModel.followedEvents.observe(viewLifecycleOwner){ followedEvents ->
            if(followedEvents != null && followedEvents.isNotEmpty()){
                var adapter = context?.let { ctx -> EventsAdapter(ctx, followedEvents) }
                binding.followedEventsList.adapter = adapter
                binding.followedEventsList.setOnItemClickListener { adapterView, view, index, id ->
                    //TODO - start new fragment or activity with event details
                }
            }
            else{
                binding.emptyFollowedEventsText.visibility = TextView.VISIBLE
            }
        }
    }

}