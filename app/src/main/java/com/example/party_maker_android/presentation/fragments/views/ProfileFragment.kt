package com.example.party_maker_android.presentation.fragments.views

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.databinding.FragmentProfileBinding
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
        viewModel.eventsToShow.observe(viewLifecycleOwner){
            if(it != null && it.isNotEmpty()){
                binding.noEventsText.visibility = TextView.VISIBLE
                var adapter = context?.let { ctx -> EventsAdapter(ctx, it) }
                binding.eventList.adapter = adapter
                binding.eventsLoadingBar.visibility = ProgressBar.INVISIBLE
                binding.eventList.visibility = ListView.VISIBLE
            }
            else{
                binding.eventList.adapter = null
                binding.eventsLoadingBar.visibility = ProgressBar.INVISIBLE
                binding.eventList.visibility = ListView.INVISIBLE
                binding.noEventsText.visibility = TextView.VISIBLE
            }
        }
        /*viewModel.loading.observe(viewLifecycleOwner){
            if(it == true){
                binding.eventsLoadingBar.visibility = ProgressBar.VISIBLE
            }
            else{
                binding.eventsLoadingBar.visibility = ProgressBar.GONE
            }
        }*/
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
}